package dev.serenity.ui.mainmenu;

import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;

import java.awt.*;

public class Button {
    private final String name;

    public Button(String name) {
        this.name = name;
    }

    public void drawButton(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        Color backgroundColor = new Color(0, 0, 0, 120);
        if (HoveringUtils.isHovering(mouseX, mouseY, x, y, x2, y2)) backgroundColor = new Color(0, 0, 0, 200);
        RenderUtils.drawRoundedRect(x, y, x2, y2, 12F, backgroundColor.getRGB());
        Fonts.font20.drawString(name, (x + x2) / 2 - Fonts.font20.getStringWidth(name) / 2F, (y + y2) / 2 - Fonts.font20.FONT_HEIGHT / 2F + 2F, Color.WHITE.getRGB());
    }

    public String getName() {
        return name;
    }
}
