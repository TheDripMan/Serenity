package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.utilities.player.MovementUtils;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    private final ModeSetting test = new ModeSetting("test", new String[]{"aaaaaaaaaaaaaaaaa", "aaaaaaaa"}, "aaaaaaaaaaaaaaaaa", this);

    public Sprint() {
        super("Sprint", "Automatically sprints all the time.", Category.MOVEMENT, Keyboard.KEY_NONE, true);
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        final boolean canSprint = MovementUtils.canSprint(mc.thePlayer);
        mc.thePlayer.setSprinting(canSprint);
    }

    @Override
    public void onDisable()
    {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
    }
}
