package com.morotech.javachallenge.utils;

import java.time.Month;
import java.util.Optional;

import static com.morotech.javachallenge.utils.MoroConstant.UNKNOWN_MONTH;

public class MoroUtil {

    public static String toMonthNameBy(Integer month) {
        return Optional.ofNullable(month)
                .map(monthValue -> Month.of(monthValue).name())
                .orElse(UNKNOWN_MONTH);
    }
}
