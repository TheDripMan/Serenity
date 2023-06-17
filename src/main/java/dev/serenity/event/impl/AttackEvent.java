package dev.serenity.event.impl;

import dev.serenity.event.Event;
import net.minecraft.entity.Entity;

public class AttackEvent extends Event {
    private final Entity target;

    public AttackEvent(Entity target) {
        this.target = target;
    }

    public Entity getTarget() {
        return target;
    }
}
