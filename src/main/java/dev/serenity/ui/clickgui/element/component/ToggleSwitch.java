package dev.serenity.ui.clickgui.element.component;

import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;

import java.awt.*;

public class ToggleSwitch {
    public boolean state;

    public void onDraw(int mouseX, int mouseY, float x, float y, float width, float height) {
        Color circleColor = state ? new Color(0, 0, 0) : new Color(206, 206, 206);
        float circleX = state ? (x + width - (2F + (height - 4F) / 2F)) : (x + (2F + (height - 4F) / 2F));
        Color accentColor = HoveringUtils.isHovering(mouseX, mouseY, x, y, x + width, y + height) ? new Color(76, 194, 255) : new Color(72, 178, 233);
        Color disabledAccentColor = HoveringUtils.isHovering(mouseX, mouseY, x, y, x + width, y + height) ? new Color(39, 39, 39) : new Color(52, 52, 52);
        Color borderColor = new Color(158, 158, 158);

        if (state) {
            RenderUtils.drawRoundedRect(x - 0.5F, y - 0.5F, x + width + 0.5F, y + height + 0.5F, height / 2F, accentColor.getRGB());
        } else {
            RenderUtils.drawRoundedRectWithBorder(x, y, x + width, y + height, height / 2F, disabledAccentColor.getRGB(), borderColor.getRGB() , 0.5F);
        }

        RenderUtils.drawFilledCircle(circleX, y + 2F + (height - 4F) / 2F, (height - 4F) / 2F, circleColor);
    }
}
