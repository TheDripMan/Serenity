package dev.serenity.event.impl;

import dev.serenity.event.Event;

public class MoveEvent extends Event {
    private double x;
    private double y;
    private double z;
    private boolean isSafeWalk;

    public MoveEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void zero() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
    }

    public void zeroXZ() {
        x = 0.0;
        z = 0.0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isSafeWalk() {
        return isSafeWalk;
    }

    public void setSafeWalk(boolean safeWalk) {
        isSafeWalk = safeWalk;
    }
}

