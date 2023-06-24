package dev.serenity.ui.clickgui;

import dev.serenity.module.Category;
import dev.serenity.ui.clickgui.element.CategoryElement;
import dev.serenity.ui.clickgui.element.SearchElement;
import dev.serenity.utilities.animation.Animate;
import dev.serenity.utilities.animation.Easing;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGui extends GuiScreen {
    private final ArrayList<CategoryElement> categoryElements;
    private SearchElement searchElement;
    private int x, y, x2, y2;
    private final Animate openingAnim;

    @Override
    public void initGui() {
        x = this.width < 960 ? 10 : 200; y = this.height < 487 ? 10 : 60;
        x2 = this.width - x; y2 = this.height - y;

        searchElement = new SearchElement(x, y, x2,y + 60);
    }

    public ClickGui() {
        categoryElements = new ArrayList<>();
        openingAnim = new Animate();

        for (Category category : Category.values()) {
            categoryElements.add(new CategoryElement(category));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        openingAnim.setEase(Easing.SINE_IN).setMin(50).setMax(500).setSpeed(1000).setReversed(false).update();

        ScaledResolution sr = new ScaledResolution(mc);

        RenderUtils.scale(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, openingAnim.getValue() / 500, () -> {
            RenderUtils.drawRoundedRect(x, y, x2, y2, 8F, new Color(32, 32, 32).getRGB());

            if (!searchElement.isTyping()) {
                float startY = y + 60F;
                for (CategoryElement categoryElement : categoryElements) {
                    categoryElement.drawElement(mouseX, mouseY, x, startY, x2, startY + 25F, y2, Mouse.getDWheel(), y + 60F);
                    startY += 30F;
                }
            }

            searchElement.drawElement(mouseX, mouseY, x, y, x2, y + 60F, categoryElements, y2, Mouse.getDWheel());
        });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        searchElement.handleMouseClick(mouseX, mouseY, x, y, x2, y + 60F, categoryElements);

        if (!searchElement.isTyping()) {
            float startY = y + 60F;
            for (CategoryElement categoryElement : categoryElements) {
                categoryElement.handleMouseClick(mouseX, mouseY, x, startY, x2, startY + 25F, y + 60F, y2);
                startY += 30F;
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }

        searchElement.handleTyping(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
