package dev.serenity.module.impl.render;

import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.ui.clickgui.ClickGui;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "Opens the ClickGUI.", Category.RENDER, Keyboard.KEY_RSHIFT, false);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGui());
        this.setState(false);
    }
}
