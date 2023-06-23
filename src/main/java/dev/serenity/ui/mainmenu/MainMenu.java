package dev.serenity.ui.mainmenu;

import dev.serenity.Serenity;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.other.HoveringUtils;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends GuiScreen {
    private final ResourceLocation backgroundLocation = new ResourceLocation("serenity/mainmenu/blurBg.jpg");
    private final ArrayList<Button> buttons = new ArrayList<>();

    @Override
    public void initGui() {
        buttons.clear();
        buttons.add(new Button("Singleplayer"));
        buttons.add(new Button("Multiplayer"));
        buttons.add(new Button("Alt Manager"));
        buttons.add(new Button("Settings"));
        buttons.add(new Button("Exit"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(backgroundLocation);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);

        Fonts.fontBold60.drawString(Serenity.getInstance().getName(), this.width / 2F - Fonts.fontBold60.getStringWidth(Serenity.getInstance().getName()) / 2F, 100, Color.WHITE.getRGB());

        float startY = 160;
        for (Button button : buttons) {
            button.drawButton(mouseX, mouseY, 400, startY, this.width - 400, startY + 35F);
            startY += 45F;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float startY = 160;
        for (Button button : buttons) {
            if (HoveringUtils.isHovering(mouseX, mouseY, 400, startY, this.width - 400, startY + 35F)) {
                switch (button.getName()) {
                    case "Singleplayer": {
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    }
                    case "Multiplayer": {
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    }
                    case "Alt Manager": {
                        break;
                    }
                    case "Settings": {
                        mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                        break;
                    }
                    case "Exit": {
                        mc.shutdown();
                        break;
                    }
                }
            }
            startY += 45F;
        }
    }
}
