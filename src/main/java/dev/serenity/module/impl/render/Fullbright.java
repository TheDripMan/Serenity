package dev.serenity.module.impl.render;

import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.BooleanSetting;
import dev.serenity.setting.impl.ModeSetting;
import dev.serenity.setting.impl.NumberSetting;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

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

    public Fullbright() {
        super("Fullbright", "Brightens up the world around you.", Category.RENDER, Keyboard.KEY_NONE, false);
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.gameSettings.gammaSetting = 100;
    }
}
