package dev.serenity.utilities.math;

public class TimerUtils {
    private long lastMS;

    public TimerUtils() {
        this.lastMS = System.currentTimeMillis();
    }

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean hasPassed(long time, boolean reset) {
        long currentMS = System.currentTimeMillis();
        if(currentMS - lastMS > time) {
            if(reset)
                reset();
            return true;
        }
        return false;
    }
}
