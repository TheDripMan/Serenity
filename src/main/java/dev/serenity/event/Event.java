package dev.serenity.event;

import net.minecraft.client.Minecraft;

public class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancelEvent() {
        this.cancelled = true;
    }

    public void call() {
        if (Minecraft.getMinecraft() != null) EventHandler.handle(this);
    }
}
