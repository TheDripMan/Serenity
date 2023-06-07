package dev.serenity.utilities.math;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {
    public static float round(final float value, final float places) {
        if (places < 0) throw new IllegalArgumentException();

        final float precision = 1 / places;
        return Math.round(value * precision) / precision;
    }

    public static double getRandom(double min, double max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            final double d = min;
            min = max;
            max = d;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
