package dev.serenity.module.impl.render;

import dev.serenity.Serenity;
import dev.serenity.event.impl.Render2DEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.setting.impl.BooleanSetting;
import org.lwjgl.input.Keyboard;

public class HUD extends Module {
    private final BooleanSetting test = new BooleanSetting("test", false, this);

    public HUD() {
        super("HUD", "Toggles visibility of the HUD.", Category.RENDER, Keyboard.KEY_NONE, true);
        this.setHidden(true);
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        Serenity.getInstance().getHud().render();
    }
}
