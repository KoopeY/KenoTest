package ru.koopey.test_keno.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.koopey.test_keno.game.Game;
import ru.koopey.test_keno.model.KenoGameProtobuf;
import ru.koopey.test_keno.model.Rate;

@RestController
@RequestMapping("/game")
public class GameController {
    private static Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private Game game;

    @RequestMapping(value = "/newRate", method = RequestMethod.POST)
    public Boolean newRate(@RequestBody Rate rate, @CookieValue("keno") String session) {
        logger.info(String.format("Get new rate: %s , kenoPlayer = %s", rate.toString(), session));
        rate.setKenoPlayer(session);
        return game.addRate(rate);
    }

    @RequestMapping(value = "/getRoundInfo", method = RequestMethod.POST)
    public KenoGameProtobuf.Keno getRoundInfo(@CookieValue("keno") String session) {
        return game.getRoundInfo(session);
    }
}
