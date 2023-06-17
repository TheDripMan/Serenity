package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;

public class Fly extends Module {
    public Fly() {
        super("Fly", "idk.", Category.MOVEMENT, 0, false);
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.capabilities.setFlySpeed(2 * 0.05f);

        if (mc.gameSettings.keyBindJump.isKeyDown()) mc.thePlayer.motionY += 2;

        if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.thePlayer.motionY -= 2;
    }
}
