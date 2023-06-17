package dev.serenity.ui.clickgui.element.component;

import dev.serenity.utilities.other.HoveringUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class Checkbox {
    public boolean state;
    private final ResourceLocation checkmark = new ResourceLocation("serenity/clickgui/checkmark.png");

    public void onDraw(int mouseX, int mouseY, float x, float y, float side) {
        Color color;
        if (state) {
            color = HoveringUtils.isHovering(mouseX, mouseY, x, y, x + side, y + side) ? new Color(72, 178, 233) : new Color(76, 193, 254);
            RenderUtils.drawRoundedRect(x - 0.5F, y - 0.5F, x + side + 0.5F, y + side + 0.5F, 4F, color.getRGB());
            RenderUtils.drawImage(checkmark, x + 2F, y + 4F, side - 4F, side - 4F);
        } else {
            color = HoveringUtils.isHovering(mouseX, mouseY, x, y, x + side, y + side) ? new Color(52, 52, 52) : new Color(39, 39, 39);
            RenderUtils.drawRoundedRectWithBorder(x, y, x + side, y + side, 4F, color.getRGB(), new Color(158, 158, 158).getRGB(), 0.5F);
        }
    }
}
