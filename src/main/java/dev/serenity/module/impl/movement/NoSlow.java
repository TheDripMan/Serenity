package dev.serenity.module.impl.movement;

import dev.serenity.Serenity;
import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.event.impl.SlowDownEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.module.impl.combat.KillAura;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.utilities.player.PacketUtils;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"Vanilla", "Grim"}, "Vanilla", this);

    public NoSlow() {
        super("NoSlow", "Makes you move with full speed when using items", Category.MOVEMENT, Keyboard.KEY_NONE, false);
    }

    @Override
    public void onPreMotion(PreMotionEvent event) {
        KillAura killAura = Serenity.getInstance().getModuleManager().getModule(KillAura.class);

        if(mc.thePlayer == null || mc.theWorld == null)
            return;

        if (mode.getCurrentMode().equals("Grim")) {
            if (mc.thePlayer.isBlocking() || killAura.blocking) {
                PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }
        }
    }

    @Override
    public void onSlowDown(SlowDownEvent event) {
        if(mc.thePlayer == null || mc.theWorld == null) return;

        event.setForward(1);
        event.setStrafe(1);
    }

    @Override
    public String getSuffix() {
        return mode.getCurrentMode();
    }
}
