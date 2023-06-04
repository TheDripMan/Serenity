package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.utilities.player.MovementUtils;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"Legit"}, "Legit", this);

    public Speed() {
        super("Speed", "Allows you to move faster.", Category.MOVEMENT, Keyboard.KEY_X, false);
    }

    @Override
    public void onPreMotion(PreMotionEvent event) {
        switch (mode.getCurrentMode()) {
            case "Legit": {
                mc.gameSettings.keyBindJump.setPressed(MovementUtils.isMoving());
                break;
            }
        }
    }

    @Override
    public void onDisable() {
        switch (mode.getCurrentMode()) {
            case "Legit": {
                mc.gameSettings.keyBindJump.setPressed(Keyboard.isKeyDown(Keyboard.KEY_SPACE));
                break;
            }
        }
    }

    @Override
    public String getSuffix() {
        return mode.getCurrentMode();
    }
}
