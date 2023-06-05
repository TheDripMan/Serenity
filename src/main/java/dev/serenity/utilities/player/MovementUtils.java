package dev.serenity.utilities.player;

import dev.serenity.utilities.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;

public class MovementUtils extends MinecraftInstance {
    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }

    public static boolean canSprint(EntityPlayerSP player) {
        return player.movementInput.moveForward >= 0.8f && (player.getFoodStats().getFoodLevel() > 6.0f
                || player.capabilities.allowFlying) && !player.isPotionActive(Potion.blindness)
                && !player.isCollidedHorizontally
                && !player.isSneaking();
    }
}
