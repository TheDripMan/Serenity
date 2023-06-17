package dev.serenity.ui.clickgui.element;

import dev.serenity.ui.font.Fonts;
import net.minecraft.client.gui.GuiTextField;

public class SearchBox extends GuiTextField {
    public SearchBox(int componentId, int x, int y, int width, int height) {
        super(componentId, Fonts.font20, x, y, width, height, false);
    }

    @Override
    public boolean getEnableBackgroundDrawing() {
        return false;
    }
}
