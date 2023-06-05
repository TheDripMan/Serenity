package dev.serenity.module.impl.combat;

import dev.serenity.event.impl.PacketEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.ModeSetting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"Cancel"}, "Cancel", this);
    public Velocity() {
        super("Velocity", "Allows you to modify the amount of knockback you take.", Category.COMBAT, Keyboard.KEY_NONE, false);
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
                }
            }
        }
    }

    @Override
    public String getSuffix() {
        return mode.getCurrentMode();
    }
}
