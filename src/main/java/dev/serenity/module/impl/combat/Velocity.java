package dev.serenity.module.impl.combat;

import dev.serenity.event.impl.PacketEvent;
import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.ModeSetting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
    private int grimTick = 0;
    private int grimUpdateTick = 0;

    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"Cancel", "Grim", "Hypixel"}, "Cancel", this);

    public Velocity() {
        super("Velocity", "Allows you to modify the amount of knockback you take.", Category.COMBAT, Keyboard.KEY_NONE, false);
    }

    @Override
    public void onEnable() {
        grimTick = 0;
    }

    @Override
    public void onPacket(PacketEvent event) {
        final Packet<?> p = event.getPacket();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity packet = (S12PacketEntityVelocity) p;

            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                switch (mode.getCurrentMode()) {
                    case "Cancel": {
                        event.cancelEvent();
                        break;
                    }
                    case "Grim": {
                        event.cancelEvent();
                        grimTick = 10;
                        break;
                    }
                    case "Hypixel": {
                        event.cancelEvent();
                        if (mc.thePlayer.onGround) mc.thePlayer.motionY = packet.getMotionY() / 8000.0;
                        break;
                    }
                }
            }
        }
        if (p instanceof S32PacketConfirmTransaction) {

            if (mode.getCurrentMode().equals("Grim")) {
                if (grimTick > 0) {
                    event.cancelEvent();
                    grimTick--;
                }
            }
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mode.getCurrentMode().equals("Grim")) {
            grimUpdateTick++;

            if (grimUpdateTick >= 0) {
                grimUpdateTick = 0;
                if (grimTick > 0) {
                    grimTick--;
                }
            }
        }
    }

    @Override
    public String getSuffix() {
        return mode.getCurrentMode();
    }
}
