package dev.serenity.utilities.other;

import dev.serenity.utilities.MinecraftInstance;
import net.minecraft.util.ChatComponentText;

public class ChatUtils extends MinecraftInstance {
    public static void addMessage(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }
}
