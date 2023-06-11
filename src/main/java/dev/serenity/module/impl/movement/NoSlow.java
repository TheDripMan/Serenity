package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.SlowDownEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", "Makes you move with full speed when using items", Category.MOVEMENT, Keyboard.KEY_R, false);
    }

    @Override
    public void onSlowDown(SlowDownEvent event) {
        event.setForward(1);
        event.setStrafe(1);
    }
}
