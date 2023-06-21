package dev.serenity.module.impl.combat;

import dev.serenity.event.impl.AttackEvent;
import dev.serenity.event.impl.PostMotionEvent;
import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.utilities.math.RandomUtils;
import dev.serenity.utilities.math.TimerUtils;
import dev.serenity.utilities.player.PacketUtils;
import dev.serenity.utilities.player.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    private final TimerUtils timer = new TimerUtils();
    private EntityLivingBase target;
    public boolean blocking;
    public static boolean attacking = false;
    private double targetPosX, targetPosY, targetPosZ;
    public static float yaw, pitch, lastYaw, lastPitch;
    private float randomYaw, randomPitch;

    private final NumberSetting minCPS = new NumberSetting("Min CPS", 12, 1, 20, 1, this) {
        @Override
        public void set() {
            if (minCPS.getValue() > maxCPS.getValue()) {
                minCPS.setValue(maxCPS.getValue());
            }
        }
    };
    private final NumberSetting maxCPS = new NumberSetting("Max CPS", 15, 1, 20, 1, this) {
        @Override
        public void set() {
            if (maxCPS.getValue() < minCPS.getValue()) {
                maxCPS.setValue(minCPS.getValue());
            }
        }
    };
    private final NumberSetting range = new NumberSetting("Range",5,2.0f,5.0f,0.05f,this);
    private final ModeSetting priority = new ModeSetting("Priority",new String[]{"Distance", "Health"},"Distance",this);
    private final BooleanSetting playersOnly = new BooleanSetting("Players Only", false, this);
    private final BooleanSetting invisibles = new BooleanSetting("Invisibles", true, this);
    public final ModeSetting blockMode = new ModeSetting("Block Mode", new String[]{"None", "Fake", "Grim"}, "Fake",this);
    private final ModeSetting blockTiming = new ModeSetting("Block Timing", new String[]{"Pre", "Post"}, "Post", this, () -> !blockMode.getCurrentMode().equals("None"));
    private final BooleanSetting swing = new BooleanSetting("Swing", true, this);
    private final BooleanSetting silentRotations = new BooleanSetting("Silent Rotation", true, this);
    private final NumberSetting minYawRotation = new NumberSetting("Min Yaw Rot", 180, 1, 180, 0.1F, this) {
        @Override
        public void set() {
            if (minYawRotation.getValue() > maxYawRotation.getValue()) {
                minYawRotation.setValue(maxYawRotation.getValue());
            }
        }
    };
    private final NumberSetting maxYawRotation = new NumberSetting("Max Yaw Rot", 180, 1, 180, 0.1F, this) {
        @Override
        public void set() {
            if (maxYawRotation.getValue() < minYawRotation.getValue()) {
                maxYawRotation.setValue(minYawRotation.getValue());
            }
        }
    };
    private final NumberSetting minPitchRotation = new NumberSetting("Min Pitch Rot", 180, 1, 180, 0.1F, this) {
        @Override
        public void set() {
            if (minPitchRotation.getValue() > maxPitchRotation.getValue()) {
                minPitchRotation.setValue(maxPitchRotation.getValue());
            }
        }
    };
    private final NumberSetting maxPitchRotation = new NumberSetting("Max Pitch Rot", 180, 1, 180, 0.1F, this) {
        @Override
        public void set() {
            if (maxPitchRotation.getValue() < minPitchRotation.getValue()) {
                maxPitchRotation.setValue(minPitchRotation.getValue());
            }
        }
    };
    private final NumberSetting predict = new NumberSetting("Predict", 0, 0, 4, 0.1F, this);
    private final NumberSetting random = new NumberSetting("Random", 0, 0, 18, 0.1F, this);

    public KillAura() {
        super("KillAura", "Attacks niggas.", Category.COMBAT, Keyboard.KEY_R, false);
    }

    @Override
    public void onDisable() {
        unblock();
        attacking = false;
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer == null) {
            return;
        }

        lastYaw = mc.thePlayer.rotationYaw;
        lastPitch = mc.thePlayer.rotationPitch;
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if(target != null && isValidTarget(target)) {
            targetPosX = target.posX;
            targetPosY = target.posY;
            targetPosZ = target.posZ;
        }
    }

    @Override
    public void onPostMotion(PostMotionEvent event) {
        if(target != null && isValidTarget(target)) {
            if (blockTiming.getCurrentMode().equals("Post")) block();
        }
    }

    @Override
    public void onPreMotion(PreMotionEvent event) {
        updateTargets();
        if(target != null && isValidTarget(target)) {
            update();

            if (silentRotations.isEnabled()) {
                event.setYaw(yaw);
                event.setPitch(pitch);
            } else {
                mc.thePlayer.rotationYaw = yaw;
                mc.thePlayer.rotationPitch = pitch;
            }

            if (blockTiming.getCurrentMode().equals("Pre")) block();

            if(timer.hasPassed(1000 / Math.round(RandomUtils.nextDouble(minCPS.getValue(), maxCPS.getValue())))) {
                timer.reset();

                attacking = true;
                if (this.swing.isEnabled()) mc.thePlayer.swingItem();
                PacketUtils.sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

                final AttackEvent attackEvent = new AttackEvent(target);
                attackEvent.call();
            }
        } else {
            unblock();
            attacking = false;
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
        if (!blockMode.getCurrentMode().equals("None")) {
            if(!isHoldingSword())
                return;
            mc.thePlayer.setItemInUse(mc.thePlayer.inventory.getCurrentItem(), mc.thePlayer.inventory.getCurrentItem().getMaxItemUseDuration());
            blocking = true;
            switch (blockMode.getCurrentMode()) {
                case "Grim": {
                    mc.gameSettings.keyBindUseItem.setPressed(true);
                    break;
                }
            }
        }
    }

    private void unblock()
    {
        if (!blockMode.getCurrentMode().equals("None")) {
            blocking = false;
            switch (blockMode.getCurrentMode()) {
                case "Grim": {
                    mc.gameSettings.keyBindUseItem.setPressed(false);
                    break;
                }
            }
        }
    }

    private void updateRotations() {
        lastYaw = yaw;
        lastPitch = pitch;

        final float[] rotations = this.getRotations();

        yaw = rotations[0];
        pitch = rotations[1];
    }


    private void update() {
        if (target == null) {
            lastYaw = mc.thePlayer.rotationYaw;
            lastPitch = mc.thePlayer.rotationPitch;
        } else {
            this.updateRotations();
        }
    }

    private float[] getRotations() {
        final double predictValue = predict.getValue();

        final double x = (targetPosX - (target.lastTickPosX - targetPosX) * predictValue) + 0.01 - mc.thePlayer.posX;
        final double z = (targetPosZ - (target.lastTickPosZ - targetPosZ) * predictValue) - mc.thePlayer.posZ;

        double minus = (mc.thePlayer.posY - targetPosY);

        if (minus < -1.4) minus = -1.4;
        if (minus > 0.1) minus = 0.1;

        final double y = (targetPosY - (target.lastTickPosY - targetPosY) * predictValue) + 0.4 + target.getEyeHeight() / 1.3 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight()) + minus;

        final double xzSqrt = MathHelper.sqrt_double(x * x + z * z);

        float yaw = MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(z, x)) - 90.0F);
        float pitch = MathHelper.wrapAngleTo180_float((float) Math.toDegrees(-Math.atan2(y, xzSqrt)));

        final double randomAmount = random.getValue();

        if (randomAmount != 0) {
            randomYaw += ((Math.random() - 0.5) * randomAmount) / 2;
            randomYaw += ((Math.random() - 0.5) * randomAmount) / 2;
            randomPitch += ((Math.random() - 0.5) * randomAmount) / 2;

            if (mc.thePlayer.ticksExisted % 5 == 0) {
                randomYaw = (float) (((Math.random() - 0.5) * randomAmount) / 2);
                randomPitch = (float) (((Math.random() - 0.5) * randomAmount) / 2);
            }

            yaw += randomYaw;
            pitch += randomPitch;
        }

        final int fps = (int) (Minecraft.getDebugFPS() / 20.0F);

        final float advancedYawDistance = RandomUtils.nextFloat(this.minYawRotation.getValue(), this.maxYawRotation.getValue());
        final float advancedPitchDistance = RandomUtils.nextFloat(this.minPitchRotation.getValue(), this.maxPitchRotation.getValue());

        final float advancedDeltaYaw = (((yaw - lastYaw) + 540) % 360) - 180;
        final float advancedDeltaPitch = pitch - lastPitch;

        final float advancedDistanceYaw = MathHelper.clamp_float(advancedDeltaYaw, -advancedYawDistance, advancedYawDistance) / fps * 4;
        final float advancedDistancePitch = MathHelper.clamp_float(advancedDeltaPitch, -advancedPitchDistance, advancedPitchDistance) / fps * 4;

        yaw = lastYaw + advancedDistanceYaw;
        pitch = lastPitch + advancedDistancePitch;

        final float[] rotations = new float[]{yaw, pitch};
        final float[] lastRotations = new float[]{KillAura.yaw, KillAura.pitch};

        final float[] fixedRotations = RotationUtils.getFixedRotation(rotations, lastRotations);

        yaw = fixedRotations[0];
        pitch = fixedRotations[1];

        pitch = MathHelper.clamp_float(pitch, -90.0F, 90.0F);

        return new float[]{yaw, pitch};
    }
}
