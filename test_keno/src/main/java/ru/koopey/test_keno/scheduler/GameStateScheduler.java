package ru.koopey.test_keno.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.koopey.test_keno.game.Game;
import ru.koopey.test_keno.utils.RequestUtils;

@Component
public class GameStateScheduler {
    private static Logger logger = LoggerFactory.getLogger(GameStateScheduler.class);

    @Autowired
    private Game game;

    @Scheduled(fixedRate = 1000)
    public void updateState() {
        game.updateGame(RequestUtils.getGameState());
    }
}