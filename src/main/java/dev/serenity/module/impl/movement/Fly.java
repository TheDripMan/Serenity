package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.BlockBBEvent;
import dev.serenity.event.impl.MoveEvent;
import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
    private double launchY = 0;

    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"3fmc"}, "3fmc", this);
    private final BooleanSetting timerBoost = new BooleanSetting("Timer Boost", false, this, () -> mode.getCurrentMode().equals("3fmc"));

    public Fly() {
        super("Fly", "Allows you to fly in survival mode.", Category.MOVEMENT, Keyboard.KEY_NONE, false);
    }

    @Override
    public void onEnable() {
        launchY = mc.thePlayer.posY;
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1f;
    }

    @Override
    public void onMove(MoveEvent event) {
        if (mode.getCurrentMode().equals("3fmc")) {
            mc.timer.timerSpeed = 1f;

            if (timerBoost.isEnabled()) {
                if(mc.thePlayer.ticksExisted % 20 < 10) {
                    mc.timer.timerSpeed = 1.25f;
                } else {
                    mc.timer.timerSpeed = 0.8f;
                }
            }

            mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), 1, null, 0f, 1f, 0f));
        }
    }

    @Override
    public void onPreMotion(PreMotionEvent event) {
        if (mode.getCurrentMode().equals("3fmc")) {
            event.setYaw(mc.thePlayer.rotationYaw);
            event.setPitch(90f);
        }
    }

    @Override
    public void onBlockBB(BlockBBEvent event) {
        if (mode.getCurrentMode().equals("3fmc")) {
            if (event.getBlock() instanceof BlockAir && event.getY() <= launchY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0, launchY, event.getZ() + 1.0));
            }
        }
    }

    @Override
    public String getSuffix() {
        return mode.getCurrentMode();
    }
}
