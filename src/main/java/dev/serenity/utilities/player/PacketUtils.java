package dev.serenity.utilities.player;

import dev.serenity.utilities.MinecraftInstance;
import net.minecraft.network.Packet;

public class PacketUtils extends MinecraftInstance {

    public static void sendPacket(Packet<?> p)
    {
        mc.thePlayer.sendQueue.addToSendQueue(p);
    }

    public static void sendPacketNoEvent(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueueNoEvent(packet);
    }
}
