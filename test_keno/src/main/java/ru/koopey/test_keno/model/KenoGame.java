package ru.koopey.test_keno.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
public class KenoGame implements Serializable {
    private Game game;
    private HashMap<Integer, Integer> bets;
    private List<History> history;
    private String version;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    @Data
    public class Game {
        private Long round;
        private Integer timer;
        private List<Integer> balls;
    }

    @Data
    public static class History implements Serializable {
        private Long id;
        private String ball;
    }
}