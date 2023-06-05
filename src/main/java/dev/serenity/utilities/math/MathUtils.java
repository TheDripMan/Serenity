package dev.serenity.utilities.math;

public class MathUtils {
    public static float round(final float value, final float places) {
        if (places < 0) throw new IllegalArgumentException();

        final float precision = 1 / places;
        return Math.round(value * precision) / precision;
    }
}
