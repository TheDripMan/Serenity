package dev.serenity.ui.clickgui;

import dev.serenity.Serenity;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.module.impl.render.ClickGUI;
import dev.serenity.ui.clickgui.element.CategoryElement;
import dev.serenity.ui.clickgui.element.SearchElement;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.render.RenderUtils;
import dev.serenity.utilities.render.Stencil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class ClickGui extends GuiScreen {
    private final ArrayList<CategoryElement> categoryElements = new ArrayList<>();
    private final SearchElement searchElement;

    public ClickGui() {
        for (Category category : Category.values()) {
            categoryElements.add(new CategoryElement(category));
        }
        searchElement = new SearchElement(160, 150, 325, 170);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawRoundedRect(150F, 60F, this.width - 150F, this.height - 60F, 4F, new Color(239, 244, 249).getRGB());

        // player avatar
        Stencil.write(true);
        RenderUtils.drawFilledCircle(184F, 105F, 25F, new Color(239, 244, 249));
        Stencil.erase(true);
        if (mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()) != null) {
            final ResourceLocation skin = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getLocationSkin();
            glPushMatrix();
            glTranslatef(40F, 55F, 0F);
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_BLEND);
            glDepthMask(false);
            OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
            glColor4f(1f, 1f, 1f, 1f);
            mc.getTextureManager().bindTexture(skin);
            Gui.drawScaledCustomSizeModalRect(119F, 25F, 8F, 8F, 8, 8, 50, 50,
                    64F, 64F);
            glDepthMask(true);
            glDisable(GL_BLEND);
            glEnable(GL_DEPTH_TEST);
            glPopMatrix();
        }
        Stencil.dispose();

        Fonts.fontBold30.drawString(mc.thePlayer.getGameProfile().getName(), 215F, (60 + 150) / 2F - Fonts.fontBold30.FONT_HEIGHT / 2F + 2F , new Color(25, 26, 26).getRGB());

        searchElement.drawElement(mouseX, mouseY, this.width - 150F, this.height - 60F, categoryElements);

        float count = 6F;
        if (searchElement.searched) {
            count = 5F;

            if (Category.selectedCategory == null) {
                searchElement.setPosition(355, 120, 520, 140);
            } else {
                searchElement.setPosition(160, 150, 325, 170);

                count = 6F;
                searchElement.searched = false;
                Module.selectedModule = null;
            }
            searchElement.searchBox.xPosition = searchElement.left + 7;
            searchElement.searchBox.yPosition = (searchElement.top + searchElement.bottom) / 2 - 5;

        }

        for (CategoryElement categoryElement : categoryElements) {
            categoryElement.drawElement(mouseX, mouseY, 150F, 30 * count, this.width - 150F, this.height - 60F, Mouse.getDWheel());
            count += 1.2F;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            Serenity.getInstance().getModuleManager().getModule(ClickGUI.class).setState(false);
        }

        searchElement.handleTyping(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        float count = 6F;
        if (searchElement.searched) {
            count = 5F;

            if (Category.selectedCategory != null) {
                count = 6F;
            }
        }

        searchElement.handleMouseClick(mouseX, mouseY, mouseButton, this.width - 150F, this.height - 60F, categoryElements);
        for (CategoryElement categoryElement : categoryElements) {
            categoryElement.handleMouseClick(mouseX, mouseY, mouseButton, 150F, 30 * count, this.width - 150F, this.height - 60F);
            count += 1.2F;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
