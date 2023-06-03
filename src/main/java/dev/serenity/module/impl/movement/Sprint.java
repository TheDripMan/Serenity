package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.utilities.player.MovementUtils;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    private NumberSetting test = new NumberSetting("test", 2, 2, 20, 0.5F, this);
    public Sprint() {
        super("Sprint", "Automatically sprints all the time.", Category.MOVEMENT, Keyboard.KEY_NONE, true);
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.gameSettings.keyBindSprint.setPressed(MovementUtils.isMoving());
    }
}
