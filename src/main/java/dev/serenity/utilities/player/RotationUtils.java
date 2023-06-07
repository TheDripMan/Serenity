package dev.serenity.utilities.player;

import dev.serenity.utilities.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class RotationUtils extends MinecraftInstance {
    public static float getRotation(float from, float to, float speed) {
        float f = MathHelper.wrapAngleTo180_float(to - from);
        if (f > speed) {
            f = speed;
        }
        if (!(f < -speed)) return from + f;
        f = -speed;
        return from + f;
    }

    public static float getSensitivityMultiplier() {
        float SENSITIVITY = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        return SENSITIVITY * SENSITIVITY * SENSITIVITY * 8.0f * 0.15f;
    }
}
