package dev.serenity.module.impl.combat;

import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.utilities.math.TimerUtils;
import dev.serenity.utilities.player.PacketUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    private final TimerUtils timer = new TimerUtils();
    public static EntityLivingBase target;
    public static boolean blocking;

    private final NumberSetting range = new NumberSetting("Range",5,2.0f,5.0f,0.05f,this);
    private final ModeSetting priority = new ModeSetting("Priority",new String[]{"Distance", "Health"},"Distance",this);
    private final BooleanSetting playersOnly = new BooleanSetting("Players Only", false, this);
    private final BooleanSetting invisibles = new BooleanSetting("Invisibles", true, this);
    private final BooleanSetting autoblock = new BooleanSetting("AutoBlock",true,this);


    public KillAura() {
        super("KillAura", "Attacks niggas.", Category.COMBAT, Keyboard.KEY_R, false);
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        updateTargets();
        if(target != null && isValidTarget(target)) {
            if(autoblock.isEnabled())
            {
                block();
            }
            if(timer.hasPassed(100, true)) {
                mc.thePlayer.swingItem();
                PacketUtils.sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            }
        } else unblock();
    }

    private void updateTargets()
    {
        final List<EntityLivingBase> targets = mc.theWorld.loadedEntityList
                .stream()
                .filter(entity -> entity instanceof EntityLivingBase)
                .map(entity -> ((EntityLivingBase) entity))
                .filter(entity -> {

                    if (!(entity instanceof EntityPlayer) && playersOnly.isEnabled()) return false;
                    if (entity.isInvisible() && !invisibles.isEnabled()) return false;
                    if (entity.isDead || entity.getHealth() <= 0) return false;
                    if (mc.thePlayer == entity) return false;
                    if(mc.thePlayer.getDistanceToEntity(entity) > range.getValue() + 3) return false;
                    return true;
                })
                .sorted(Comparator.comparingDouble(entity -> {
                    switch (priority.getCurrentMode()) {
                        case "Distance":
                            return mc.thePlayer.getDistanceSqToEntity(entity);
                        case "Health":
                            return entity.getHealth();
                        default:
                            return -1;
                    }
                }))
                .collect(Collectors.toList());
        target = (targets.isEmpty() ? null : targets.get(0));
    }
    private boolean isValidTarget(EntityLivingBase entity)
    {
        return mc.thePlayer.getDistanceSqToEntity(entity) <= range.getValue()*range.getValue();
    }

    private boolean isHoldingSword()
    {
        return mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }

    private void block()
    {
        if(!isHoldingSword())
            return;
        mc.thePlayer.setItemInUse(mc.thePlayer.inventory.getCurrentItem(), mc.thePlayer.inventory.getCurrentItem().getMaxItemUseDuration());
        if (!blocking) {
            PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            blocking = true;
        }
    }

    private void unblock()
    {
        if (blocking) {
            mc.playerController.onStoppedUsingItem(mc.thePlayer);
            PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            blocking = false;
        }
    }
}
