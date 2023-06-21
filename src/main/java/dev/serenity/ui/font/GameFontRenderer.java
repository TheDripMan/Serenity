package dev.serenity.ui.font;

import dev.serenity.utilities.render.ColorUtils;
import dev.serenity.utilities.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class GameFontRenderer extends FontRenderer {
    private final AWTFontRenderer defaultFont;
    private final AWTFontRenderer boldFont;
    private final AWTFontRenderer italicFont;
    private final AWTFontRenderer boldItalicFont;

    public GameFontRenderer(Font font) {
        super(Minecraft.getMinecraft().gameSettings,
                new ResourceLocation("textures/font/ascii.png"),
                Minecraft.getMinecraft().getTextureManager(),
                false);

        defaultFont = new AWTFontRenderer(font, 0, 255);
        boldFont = new AWTFontRenderer(font.deriveFont(Font.BOLD), 0, 255);
        italicFont = new AWTFontRenderer(font.deriveFont(Font.ITALIC), 0, 255);
        boldItalicFont = new AWTFontRenderer(font.deriveFont(Font.BOLD | Font.ITALIC), 0, 255);

        FONT_HEIGHT = getHeight() / 2;
    }

    public int getHeight() {
        return defaultFont.getHeight();
    }

    public int getSize() {
        return defaultFont.getFont().getSize();
    }

    public void drawString(String s, float x, float y, int color) {
        drawString(s, x, y, color, false);
    }

    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        return drawString(text, x, y, color, true);
    }

    public void drawCenteredString(String s, float x, float y, int color, boolean shadow) {
        drawString(s, x - getStringWidth(s) / 2F, y, color, shadow);
    }

    public void drawCenteredString(String s, float x, float y, int color) {
        drawStringWithShadow(s, x - getStringWidth(s) / 2F, y, color);
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean shadow) {
        if (text == null) {
            return 0;
        }

        float currY = y - 3F;
        if (shadow) {
            drawText(text, x + 1f, currY + 1f, new Color(0, 0, 0, 150).getRGB(), true);
        }
        return drawText(text, x, currY, color, false);
    }

    private int drawText(String text, float x, float y, int colorHex, boolean ignoreColor) {
        if (text == null) {
            return 0;
        }
        if (text.isEmpty()) {
            return (int) x;
        }

        GlStateManager.translate(x - 1.5, y + 0.5, 0.0);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.enableTexture2D();

        int hexColor = colorHex;
        if ((hexColor & -67108864) == 0) {
            hexColor |= -16777216;
        }

        int alpha = (hexColor >> 24) & 0xFF;

        if (text.contains("§")) {
            List<String> parts = Arrays.asList(text.split("§"));

            AWTFontRenderer currentFont = defaultFont;

            double width = 0.0;

            boolean randomCase = false;
            boolean bold = false;
            boolean italic = false;
            boolean strikeThrough = false;
            boolean underline = false;

            for (int i = 0; i < parts.size(); i++) {
                String part = parts.get(i);
                if (part.isEmpty()) {
                    continue;
                }

                if (i == 0) {
                    currentFont.drawString(part, width, 0.0, hexColor);
                    width += currentFont.getStringWidth(part);
                } else {
                    String words = part.substring(1);
                    char type = part.charAt(0);

                    int colorIndex = getColorIndex(type);
                    if (colorIndex >= 0 && colorIndex < 16) {
                        if (!ignoreColor) {
                            hexColor = ColorUtils.hexColors[colorIndex] | (alpha << 24);
                        }

                        bold = false;
                        italic = false;
                        randomCase = false;
                        underline = false;
                        strikeThrough = false;
                    } else if (colorIndex == 16) {
                        randomCase = true;
                    } else if (colorIndex == 17) {
                        bold = true;
                    } else if (colorIndex == 18) {
                        strikeThrough = true;
                    } else if (colorIndex == 19) {
                        underline = true;
                    } else if (colorIndex == 20) {
                        italic = true;
                    } else if (colorIndex == 21) {
                        hexColor = colorHex;
                        if ((hexColor & -67108864) == 0) {
                            hexColor |= -16777216;
                        }

                        bold = false;
                        italic = false;
                        randomCase = false;
                        underline = false;
                        strikeThrough = false;
                    }

                    if (bold && italic) {
                        currentFont = boldItalicFont;
                    } else if (bold) {
                        currentFont = boldFont;
                    } else if (italic) {
                        currentFont = italicFont;
                    } else {
                        currentFont = defaultFont;
                    }

                    currentFont.drawString(randomCase ? ColorUtils.randomMagicText(words) : words, width, 0.0, hexColor);

                    if (strikeThrough) {
                        RenderUtils.drawLine(width / 2.0 + 1, currentFont.getHeight() / 3.0,
                                (width + currentFont.getStringWidth(words)) / 2.0 + 1, currentFont.getHeight() / 3.0,
                                FONT_HEIGHT / 16F);
                    }

                    if (underline) {
                        RenderUtils.drawLine(width / 2.0 + 1, currentFont.getHeight() / 2.0,
                                (width + currentFont.getStringWidth(words)) / 2.0 + 1, currentFont.getHeight() / 2.0,
                                FONT_HEIGHT / 16F);
                    }

                    width += currentFont.getStringWidth(words);
                }
            }
        } else {
            defaultFont.drawString(text, 0.0, 0.0, hexColor);
        }

        GlStateManager.disableBlend();
        GlStateManager.translate(-(x - 1.5), -(y + 0.5), 0.0);
        GlStateManager.color(1f, 1f, 1f, 1f);

        return (int) (x + getStringWidth(text));
    }

    @Override
    public int getColorCode(char charCode) {
        return ColorUtils.hexColors[getColorIndex(charCode)];
    }

    @Override
    public int getStringWidth(String text) {
        if (text.isEmpty()) {
            return 0;
        }

        if (text.contains("§")) {
            List<String> parts = Arrays.asList(text.split("§"));

            AWTFontRenderer currentFont = defaultFont;
            int width = 0;
            boolean bold = false;
            boolean italic = false;

            for (int i = 0; i < parts.size(); i++) {
                String part = parts.get(i);
                if (part.isEmpty()) {
                    continue;
                }

                if (i == 0) {
                    width += currentFont.getStringWidth(part);
                } else {
                    String words = part.substring(1);
                    char type = part.charAt(0);
                    int colorIndex = getColorIndex(type);

                    if (colorIndex >= 0 && colorIndex < 16) {
                        bold = false;
                        italic = false;
                    } else if (colorIndex == 17) {
                        bold = true;
                    } else if (colorIndex == 20) {
                        italic = true;
                    } else if (colorIndex == 21) {
                        bold = false;
                        italic = false;
                    }

                    if (bold && italic) {
                        currentFont = boldItalicFont;
                    } else if (bold) {
                        currentFont = boldFont;
                    } else if (italic) {
                        currentFont = italicFont;
                    } else {
                        currentFont = defaultFont;
                    }

                    width += currentFont.getStringWidth(words);
                }
            }

            return width / 2;
        } else {
            return defaultFont.getStringWidth(text) / 2;
        }
    }

    @Override
    public int getCharWidth(char character) {
        return getStringWidth(String.valueOf(character));
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {}

    @Override
    public void bindTexture(ResourceLocation location) {}

    private static int getColorIndex(char type) {
        switch (type) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return type - '0';
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
                return type - 'a' + 10;
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
                return type - 'k' + 16;
            case 'r':
                return 21;
        }
        return -1;
    }
}
