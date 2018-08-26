package ru.koopey.test_keno.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.koopey.test_keno.enums.RateStatus;
import ru.koopey.test_keno.model.KenoGame;
import ru.koopey.test_keno.model.KenoGameProtobuf;
import ru.koopey.test_keno.model.Rate;
import ru.koopey.test_keno.repository.RateRepository;
import ru.koopey.test_keno.utils.CheckUtils;
import ru.koopey.test_keno.utils.DateUtils;

import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class Game {
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    private Queue<Rate> roundRates = new ConcurrentLinkedQueue<>();
    private volatile KenoGame currentGame;
    private AtomicBoolean acceptRates = new AtomicBoolean(false);
    private AtomicBoolean isGameReady = new AtomicBoolean(false);

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private WinCoeff winCoeff;

    public boolean addRate(Rate rate) {
        if (acceptRates.get()) {
            if (CheckUtils.checkRate(rate)) {
                roundRates.add(rate);
                new Thread(() -> createRate(rate)).start();
                return true;
            }
            logger.warn("Rate was not accepted - balls did not pass the check");
        } else {
            logger.warn("Rate was not accepted - round was ended");
        }
        return false;
    }

    public KenoGameProtobuf.Keno getRoundInfo(String playerId) {
        KenoGameProtobuf.Keno.Builder gameBuilder = KenoGameProtobuf.Keno.newBuilder();
        KenoGameProtobuf.Keno.History.Builder historyBuilder = KenoGameProtobuf.Keno.History.newBuilder();
        KenoGameProtobuf.Keno.Rate.Builder rateBuilder = KenoGameProtobuf.Keno.Rate.newBuilder();

        if (isGameReady.get()) {
            if (currentGame.getGame().getBalls() != null && !currentGame.getGame().getBalls().isEmpty()) {
                currentGame.getGame().getBalls().forEach(gameBuilder::addBalls);
            }

            if (currentGame.getHistory() != null && !currentGame.getHistory().isEmpty()) {
                currentGame.getHistory().forEach(history ->
                        gameBuilder.addHistory(
                                historyBuilder.setId(history.getId()).setBall(history.getBall()).build()
                        )
                );
            }

            List<ru.koopey.test_keno.entity.Rate> ratesByPlayerId = rateRepository.getRatesByPlayerId(playerId);
            for (ru.koopey.test_keno.entity.Rate rate: ratesByPlayerId) {
                gameBuilder.addRate(rateBuilder
                        .setBall(rate.getBalls())
                        .setRate(rate.getRate())
                        .setRound(rate.getRound())
                        .setWin(rate.getWin() == null ? 0 : rate.getWin())
                        .build()
                );
            }

            gameBuilder
                    .setRound(currentGame.getGame().getRound())
                    .setTimer(currentGame.getGame().getTimer());
        }
        return gameBuilder.build();
    }

    public void updateGame(KenoGame game) {
        this.currentGame = game;
        if (currentGame.getGame().getBalls().size() > 0) {
            acceptRates.set(false);
            calculateRound();
        } else {
            acceptRates.set(true);
        }
        isGameReady.set(true);
    }

    private void calculateRound() {
        roundRates.parallelStream().forEach(this::calculatePrize);
        roundRates.clear();
    }

    private void calculatePrize(Rate rate) {
        List<Integer> roundBalls = currentGame.getGame().getBalls();
        if (roundBalls.size() == 0) {
            logger.error("Calculate prize with no round balls");
            return;
        }

        int matched = 0;
        for (Integer ball: roundBalls) {
            if (rate.getBalls().contains(ball)) {
                matched += 1;
            }
        }

        int coeff = winCoeff.getCoeff(rate.getBalls().size(), matched);
        logger.info(String.format("Rate %s, matched = %d, prize = %d", rate.toString(), matched, rate.getRate() * coeff));

        ru.koopey.test_keno.entity.Rate dbRate = rateRepository.getRateByUuid(rate.getUuid().toString());
        if (coeff > 0) {
            dbRate.setStatus(RateStatus.WIN);
            dbRate.setWin(rate.getRate() * coeff);
        } else {
            dbRate.setStatus(RateStatus.LOSE);
        }
        dbRate.setCalculatedDate(DateUtils.currentDate());
        rateRepository.save(dbRate);
    }

    private void createRate(Rate rateInfo) {
        ru.koopey.test_keno.entity.Rate rate = new ru.koopey.test_keno.entity.Rate();
        rate.setCreatedDate(DateUtils.currentDate());
        rate.setPlayerId(rateInfo.getKenoPlayer());
        rate.setRound(rateInfo.getRound());
        rate.setBalls(rateInfo.getBalls().toString());
        rate.setStatus(RateStatus.NEW);
        rate.setRate(rateInfo.getRate());
        rate.setUuid(rateInfo.getUuid().toString());
        rateRepository.save(rate);
    }
}
