package ru.koopey.test_keno.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Rate {
    private List<Integer> balls;
    private Integer rate;
    private String kenoPlayer;
    private Long round;
    private UUID uuid;
}
