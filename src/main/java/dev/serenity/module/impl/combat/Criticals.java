package dev.serenity.module.impl.combat;

import dev.serenity.event.impl.AttackEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import org.lwjgl.input.Keyboard;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", "Makes you always get a critical hit on your opponent.", Category.COMBAT, Keyboard.KEY_NONE, false);
    }

    @Override
    public void onAttack(AttackEvent event) {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    }
}
