package dev.serenity.module.impl.world;

import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.utilities.math.MathUtils;
import dev.serenity.utilities.math.TimerUtils;
import dev.serenity.utilities.player.ItemUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class ChestStealer extends Module {
    private final TimerUtils timer = new TimerUtils();
    private long nextClick;
    private int lastClick;
    private int lastSteal;

    private final NumberSetting minDelay = new NumberSetting("Min Delay", 100, 50, 500, 25, this) {
        @Override
        public void set() {
            if (minDelay.getValue() > maxDelay.getValue()) {
                minDelay.setValue(maxDelay.getValue());
            }
        }
    };
    private final NumberSetting maxDelay = new NumberSetting("Max Delay", 150, 50, 500, 25, this) {
        @Override
        public void set() {
            if (maxDelay.getValue() < minDelay.getValue()) {
                maxDelay.setValue(minDelay.getValue());
            }
        }
    };
    private final BooleanSetting ignoreTrash = new BooleanSetting("Ignore Trash", true, this);

    public ChestStealer() {
        super("Chest Stealer", "Automatically steals all items from a chest.", Category.WORLD, Keyboard.KEY_NONE, false);
    }


    @Override
    public void onPreMotion(PreMotionEvent event) {
        if (mc.currentScreen instanceof GuiChest) {
            final ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;

            if (!this.timer.hasPassed(this.nextClick, true)) {
                return;
            }

            this.lastSteal++;

            for (int i = 0; i < container.inventorySlots.size(); i++) {
                final ItemStack stack = container.getLowerChestInventory().getStackInSlot(i);

                if (stack == null || this.lastSteal <= 1) {
                    continue;
                }

                if (this.ignoreTrash.isEnabled() && !ItemUtils.useful(stack)) {
                    continue;
                }

                this.nextClick = Math.round(MathUtils.getRandom(this.minDelay.getValue(), this.maxDelay.getValue()));
                mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
                this.lastClick = 0;
                return;
            }

            this.lastClick++;

            if (this.lastClick > 1) {
                mc.thePlayer.closeScreen();
            }
        } else {
            this.lastClick = 0;
            this.lastSteal = 0;
        }
    }
}
