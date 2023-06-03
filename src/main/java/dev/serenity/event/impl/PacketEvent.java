package dev.serenity.event.impl;

import dev.serenity.event.Event;
import net.minecraft.network.Packet;

public final class PacketEvent extends Event {
    private Packet packet;

    public PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
