package com.syncfusion.utils;

import java.util.Random;

public class Common {

    public static final double UPPER_RANGE = 800;
    public static final double LOWER_RANGE = 750;
    private static final Random random = new Random();

    public static double getRandomDouble() {
        return LOWER_RANGE
                + (random.nextDouble() * (UPPER_RANGE - LOWER_RANGE));

    }

    // for debug use only
    public static double getknownDouble() {
        double[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        return values[random.nextInt(values.length)];
    }
}
