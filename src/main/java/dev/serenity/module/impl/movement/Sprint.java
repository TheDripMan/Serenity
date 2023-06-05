package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.SprintEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.utilities.player.MovementUtils;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically sprints all the time.", Category.MOVEMENT, Keyboard.KEY_NONE, true);
    }

    @Override
    public void onSprint(SprintEvent event) {
        if (!event.isCancelled()) {
            final boolean canSprint = MovementUtils.canSprint(mc.thePlayer);
            mc.thePlayer.setSprinting(canSprint);
            event.setSprinting(canSprint);
        }
    }
}
