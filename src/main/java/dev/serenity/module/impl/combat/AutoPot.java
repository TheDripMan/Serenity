package dev.serenity.module.impl.combat;

import dev.serenity.Serenity;
import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.module.impl.world.Scaffold;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.utilities.math.TimerUtils;
import dev.serenity.utilities.player.PacketUtils;
import dev.serenity.utilities.player.PlayerUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLadder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

public class AutoPot extends Module {
    public final NumberSetting health = new NumberSetting("Health", 15, 1, 20, 1, this);
    private final BooleanSetting randomiseRots = new BooleanSetting("Randomise Rotations", true, this);
    private final BooleanSetting packet = new BooleanSetting("Extra Packet", false, this);
    private final BooleanSetting jump = new BooleanSetting("Jump", false, this);

    private int ticksSinceLastSplash, ticksSinceCanSplash, oldSlot;
    private boolean needSplash, switchBack;

    private final ArrayList<Integer> acceptedPotions = new ArrayList() {{
        add(6);
        add(1);
        add(5);
        add(8);
        add(14);
        add(12);
        add(10);
        add(16);
    }};

    public AutoPot() {
        super("AutoPot", "Automatically throw pots for you.", Category.COMBAT, Keyboard.KEY_NONE, false);
    }

    @Override
    public void onDisable() {
        needSplash = switchBack = false;
    }

    @Override
    public void onPreMotion(PreMotionEvent event) {
        ticksSinceLastSplash++;

        if (Serenity.getInstance().getModuleManager().getModule(Scaffold.class).isEnabled() || mc.thePlayer.isInLiquid() || (PlayerUtils.getBlockRelativeToPlayer(0, -1, 0) instanceof BlockAir || PlayerUtils.getBlockRelativeToPlayer(0, -1, 0) instanceof BlockLadder))
            ticksSinceCanSplash = 0;
        else
            ticksSinceCanSplash++;

        if (switchBack) {
            PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            PacketUtils.sendPacket(new C09PacketHeldItemChange(oldSlot));
            switchBack = false;
            return;
        }

        if (ticksSinceCanSplash <= 1 || !mc.thePlayer.onGround)
            return;

        oldSlot = mc.thePlayer.inventory.currentItem;
        for (int i = 36; i < 45; ++i) {
            final ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && mc.currentScreen == null) {
                final Item item = itemStack.getItem();
                if (item instanceof ItemPotion) {
                    final ItemPotion p = (ItemPotion) item;
                    if (ItemPotion.isSplash(itemStack.getMetadata())) {
                        if (p.getEffects(itemStack.getMetadata()) != null) {
                            final int potionID = p.getEffects(itemStack.getMetadata()).get(0).getPotionID();
                            boolean hasPotionIDActive = false;

                            for (final PotionEffect potion : mc.thePlayer.getActivePotionEffects()) {
                                if (potion.getPotionID() == potionID && potion.getDuration() > 0) {
                                    hasPotionIDActive = true;
                                    break;
                                }
                            }

                            if (acceptedPotions.contains(potionID) && !hasPotionIDActive && ticksSinceLastSplash > 20) {
                                final String effectName = p.getEffects(itemStack.getMetadata()).get(0).getEffectName();

                                if ((effectName.contains("regeneration") || effectName.contains("heal")) && mc.thePlayer.getHealth() > health.getValue())
                                    continue;

                                if (jump.isEnabled()) {
                                    event.setPitch(randomiseRots.isEnabled() ? -RandomUtils.nextFloat(89, 90) : -90);
                                    if (!needSplash) {
                                        mc.thePlayer.jump();
                                        needSplash = true;

                                        new Thread(() -> {
                                            try {
                                                Thread.sleep(300L); // TODO: fix
                                            } catch (final InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            if (packet.isEnabled())
                                                PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, randomiseRots.isEnabled() ? -RandomUtils.nextFloat(89, 90) : -90, mc.thePlayer.onGround));

                                            needSplash = false;
                                        }).start();
                                    } else {
                                        PacketUtils.sendPacket(new C09PacketHeldItemChange(i - 36));
                                        PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(itemStack));
                                        switchBack = true;

                                        ticksSinceLastSplash = 0;
                                        needSplash = false;
                                    }
                                } else {
                                    event.setPitch(randomiseRots.isEnabled() ? RandomUtils.nextFloat(89, 90) : 90);
                                    if (!needSplash) {
                                        if (packet.isEnabled())
                                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, randomiseRots.isEnabled() ? RandomUtils.nextFloat(89, 90) : 90, mc.thePlayer.onGround));

                                        needSplash = true;
                                    } else {
                                        PacketUtils.sendPacket(new C09PacketHeldItemChange(i - 36));
                                        PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(itemStack));
                                        switchBack = true;

                                        ticksSinceLastSplash = 0;
                                        needSplash = false;
                                    }
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
