package dev.serenity.module.impl.render;

import dev.serenity.event.impl.UpdateEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Brightens up the world around you.", Category.RENDER, Keyboard.KEY_NONE, false);
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.gameSettings.gammaSetting = 100;
    }
}
