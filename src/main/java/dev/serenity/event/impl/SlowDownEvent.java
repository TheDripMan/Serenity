package dev.serenity.event.impl;

import dev.serenity.event.Event;

public class SlowDownEvent extends Event {

    public SlowDownEvent(float strafe, float forward)
    {
        this.strafe = strafe;
        this.forward = forward;
    }

    public float getStrafe() {
        return strafe;
    }

    public float getForward() {
        return forward;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    private float strafe, forward;

}
