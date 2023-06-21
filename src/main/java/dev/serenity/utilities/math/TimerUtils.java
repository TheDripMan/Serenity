package dev.serenity.utilities.math;

public class TimerUtils {
    private long lastMS;

    public TimerUtils() {
        this.lastMS = System.currentTimeMillis();
    }

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean hasPassed(long time) {
        long currentMS = System.currentTimeMillis();
        return currentMS - lastMS > time;
    }
}
