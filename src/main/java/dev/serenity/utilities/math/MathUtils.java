package dev.serenity.utilities.math;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static float round(final float value, final float places) {
        if (places < 0) throw new IllegalArgumentException();

        final float precision = 1 / places;
        return Math.round(value * precision) / precision;
    }

    public static double getRandom(float min, float max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            final float d = min;
            min = max;
            max = d;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double getRandom(final double min, final double max) {
        return min + (RANDOM.nextDouble() * (max - min));
    }

}
