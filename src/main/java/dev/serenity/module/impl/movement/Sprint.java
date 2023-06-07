package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.event.impl.StrafeEvent;
import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.utilities.other.ChatUtils;
import dev.serenity.utilities.player.MovementUtils;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
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
