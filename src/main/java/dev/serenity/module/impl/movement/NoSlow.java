package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.SlowDownEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.ModeSetting;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"Vanilla"}, "Vanilla", this);

    public NoSlow() {
        super("NoSlow", "Makes you move with full speed when using items", Category.MOVEMENT, Keyboard.KEY_NONE, false);
    }

    @Override
    public void onSlowDown(SlowDownEvent event) {
        if(mc.thePlayer == null || mc.theWorld == null) return;

        event.setForward(1);
        event.setStrafe(1);
    }

    @Override
    public String getSuffix() {
        return mode.getCurrentMode();
    }
}
