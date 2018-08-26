package ru.koopey.test_keno.utils;

import ru.koopey.test_keno.model.Rate;

public class CheckUtils {
    public static boolean checkRate(Rate rate) {
        return rate.getBalls().stream().allMatch(ball -> ball >= 1 && ball <= 80);
    }
}
