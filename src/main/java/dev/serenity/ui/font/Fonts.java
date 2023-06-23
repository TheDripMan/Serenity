package dev.serenity.ui.font;

import dev.serenity.utilities.MinecraftInstance;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;

public class Fonts extends MinecraftInstance {
    public static final FontRenderer minecraftFont = mc.fontRendererObj;
    public static GameFontRenderer font18 = new GameFontRenderer(getFont("SegoeUIVariable.ttf", 36));
    public static GameFontRenderer font20 = new GameFontRenderer(getFont("SegoeUIVariable.ttf", 40));
    public static GameFontRenderer fontBold20 = new GameFontRenderer(getFont("SegoeUIVariableSemibold.ttf", 40));
    public static GameFontRenderer fontBold25 = new GameFontRenderer(getFont("SegoeUIVariableSemibold.ttf", 50));
    public static GameFontRenderer fontBold30 = new GameFontRenderer(getFont("SegoeUIVariableSemibold.ttf", 60));
    public static GameFontRenderer fontBold60 = new GameFontRenderer(getFont("SegoeUIVariableSemibold.ttf", 120));

    private static Font getFont(final String fontName, final int size) {
        try {
            final InputStream inputStream = mc.getResourceManager().getResource(new ResourceLocation("serenity/font/" + fontName)).getInputStream();
            Font awtClientFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtClientFont = awtClientFont.deriveFont(Font.PLAIN, size);
            inputStream.close();
            return awtClientFont;
        } catch (final Exception e) {
            e.printStackTrace();

            return new Font("default", Font.PLAIN, size);
        }
    }
}
