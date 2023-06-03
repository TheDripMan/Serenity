package dev.serenity.utilities.player;

import dev.serenity.utilities.MinecraftInstance;

public class MovementUtils extends MinecraftInstance {
    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }
}
