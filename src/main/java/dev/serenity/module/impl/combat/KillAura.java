package dev.serenity.module.impl.combat;

import dev.serenity.event.impl.PacketEvent;
import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.utilities.math.TimerUtils;
import dev.serenity.utilities.other.ChatUtils;
import dev.serenity.utilities.player.PacketUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    private final TimerUtils timer = new TimerUtils();
    public static EntityLivingBase target;

    private final NumberSetting range = new NumberSetting("Range",5,2.0f,5.0f,0.05f,this);
    private final ModeSetting priority = new ModeSetting("Priority",new String[]{"Distance", "Health"},"Distance",this);
    private final BooleanSetting playersOnly = new BooleanSetting("Players Only", false, this);
    private final BooleanSetting invisibles = new BooleanSetting("Invisibles", true, this);


    public KillAura() {
        super("KillAura", "Attacks niggas.", Category.COMBAT, Keyboard.KEY_R, false);
    }

    @Override
    public void onPacket(PacketEvent event) {
        final Packet<?> p = event.getPacket();

    }

    @Override
    public void onUpdate(UpdateEvent event) {
        updateTargets();
        if(target != null && isValidTarget(target)) {
            if(timer.hasPassed(100, true)) {
                mc.thePlayer.swingItem();
                PacketUtils.sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            }
        }
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

    private boolean isValidTarget(EntityLivingBase entity) {
        return mc.thePlayer.getDistanceToEntity(entity) <= range.getValue();
    }
}
