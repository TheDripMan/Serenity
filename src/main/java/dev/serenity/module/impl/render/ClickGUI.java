package dev.serenity.module.impl.render;

import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.NumberSetting;
import dev.serenity.ui.clickgui.ClickGui;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
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

    public ClickGUI() {
        super("ClickGUI", "Opens the ClickGUI.", Category.RENDER, Keyboard.KEY_RSHIFT, false);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGui());
    }
}
