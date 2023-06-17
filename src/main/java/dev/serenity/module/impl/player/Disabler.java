package dev.serenity.module.impl.player;

import dev.serenity.event.impl.PacketEvent;
import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NumberSetting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

public class Disabler extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"3fmc"}, "3fmc", this);

    private final NumberSetting ticks = new NumberSetting("Modify Amount", 15, 1, 30, 1, this, () -> mode.getCurrentMode().equalsIgnoreCase("3fmc"));

    public Disabler() {
        super("Disabler", "idk.", Category.PLAYER, 0, false);
    }

    @Override
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();

        switch (mode.getCurrentMode()) {
            case "3fmc": {
                if(packet instanceof C0FPacketConfirmTransaction && mc.thePlayer.ticksExisted < ticks.getValue()) {
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        ((C0FPacketConfirmTransaction) packet).setUid(Short.MIN_VALUE);
                    } else {
                        ((C0FPacketConfirmTransaction) packet).setUid(Short.MAX_VALUE);
                    }
                }
                break;
            }
        }
    }

    @Override
    public String getSuffix() {
        return mode.getCurrentMode();
    }
}
