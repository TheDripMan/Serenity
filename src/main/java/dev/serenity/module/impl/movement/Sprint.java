package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.event.impl.SprintEvent;
import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.utilities.player.MovementUtils;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    private NumberSetting test = new NumberSetting("test", 10, 1, 20, 0.5F, this);
    private ModeSetting test3 = new ModeSetting("test", new String[]{"1", "2", "3"}, "1", this);
    private BooleanSetting test2 = new BooleanSetting("test", false, this);

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
