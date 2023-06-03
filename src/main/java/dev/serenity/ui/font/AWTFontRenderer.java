package dev.serenity.ui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class AWTFontRenderer {
    private static boolean assumeNonVolatile = false;
    private static final ArrayList<AWTFontRenderer> activeFontRenderers = new ArrayList<>();
    private static int gcTicks = 0;
    private static final int GC_TICKS = 400;
    private static final int CACHED_FONT_REMOVAL_TIME = 20000;

    private Font font;
    private int fontHeight = -1;
    private CharLocation[] charLocations;
    private HashMap<String, CachedFont> cachedStrings;
    private int textureID;
    private int textureWidth;
    private int textureHeight;

    public int getHeight() {
        return (fontHeight - 8) / 2;
    }

    public Font getFont() {
        return font;
    }

    public static void garbageCollectionTick() {
        if (gcTicks++ > GC_TICKS) {
            for (AWTFontRenderer fontRenderer : activeFontRenderers) {
                fontRenderer.collectGarbage();
            }

            gcTicks = 0;
        }
    }

    private void collectGarbage() {
        long currentTime = System.currentTimeMillis();

        cachedStrings.entrySet().removeIf(entry -> {
            if (currentTime - entry.getValue().getLastUsage() > CACHED_FONT_REMOVAL_TIME) {
                GL11.glDeleteLists(entry.getValue().getDisplayList(), 1);
                entry.getValue().setDeleted(true);
                return true;
            }
            return false;
        });
    }

    public AWTFontRenderer(Font font, int startChar, int stopChar) {
        this.font = font;
        this.charLocations = new CharLocation[stopChar];
        this.cachedStrings = new HashMap<>();

        renderBitmap(startChar, stopChar);

        activeFontRenderers.add(this);
    }

    public void drawString(String text, double x, double y, int color) {
        double scale = 0.25;
        double reverse = 1 / scale;

        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        GL11.glTranslated(x * 2.0, y * 2.0 - 2.0, 0.0);
        GlStateManager.bindTexture(textureID);

        float red = (float) ((color >> 16) & 0xFF) / 255F;
        float green = (float) ((color >> 8) & 0xFF) / 255F;
        float blue = (float) (color & 0xFF) / 255F;
        float alpha = (float) ((color >> 24) & 0xFF) / 255F;

        GlStateManager.color(red, green, blue, alpha);

        double currX = 0.0;

        CachedFont cached = cachedStrings.get(text);

        if (cached != null) {
            GL11.glCallList(cached.getDisplayList());

            cached.setLastUsage(System.currentTimeMillis());

            GlStateManager.popMatrix();

            return;
        }

        int list = -1;

        if (assumeNonVolatile) {
            list = GL11.glGenLists(1);

            GL11.glNewList(list, GL11.GL_COMPILE_AND_EXECUTE);
        }

        GL11.glBegin(GL11.GL_QUADS);

        for (char ch : text.toCharArray()) {
            if (ch >= charLocations.length) {
                GL11.glEnd();

                GlStateManager.scale(reverse, reverse, reverse);
                Minecraft.getMinecraft().fontRendererObj.drawString(String.valueOf(ch), (float) (currX * scale) + 1, 2f, color, false);
                currX += Minecraft.getMinecraft().fontRendererObj.getStringWidth(String.valueOf(ch)) * reverse;

                GlStateManager.scale(scale, scale, scale);
                GlStateManager.bindTexture(textureID);
                GlStateManager.color(red, green, blue, alpha);

                GL11.glBegin(GL11.GL_QUADS);
            } else {
                CharLocation fontChar = charLocations[ch];

                if (fontChar != null) {
                    drawChar(fontChar, (float) currX, 0f);
                    currX += fontChar.width - 8.0;
                }
            }
        }

        GL11.glEnd();

        if (assumeNonVolatile) {
            cachedStrings.put(text, new CachedFont(list, System.currentTimeMillis()));
            GL11.glEndList();
        }

        GlStateManager.popMatrix();
    }

    private void drawChar(CharLocation fontChar, float x, float y) {
        float width = fontChar.width;
        float height = fontChar.height;
        float srcX = fontChar.x;
        float srcY = fontChar.y;
        float renderX = srcX / textureWidth;
        float renderY = srcY / textureHeight;
        float renderWidth = width / textureWidth;
        float renderHeight = height / textureHeight;

        GL11.glTexCoord2f(renderX, renderY);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(renderX, renderY + renderHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY + renderHeight);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY);
        GL11.glVertex2f(x + width, y);
    }

    private void renderBitmap(int startChar, int stopChar) {
        BufferedImage[] fontImages = new BufferedImage[stopChar];
        int rowHeight = 0;
        int charX = 0;
        int charY = 0;

        for (int targetChar = startChar; targetChar < stopChar; targetChar++) {
            BufferedImage fontImage = drawCharToImage((char) targetChar);
            CharLocation fontChar = new CharLocation(charX, charY, fontImage.getWidth(), fontImage.getHeight());

            if (fontChar.height > fontHeight)
                fontHeight = fontChar.height;
            if (fontChar.height > rowHeight)
                rowHeight = fontChar.height;

            charLocations[targetChar] = fontChar;
            fontImages[targetChar] = fontImage;

            charX += fontChar.width;

            if (charX > 2048) {
                if (charX > textureWidth)
                    textureWidth = charX;

                charX = 0;
                charY += rowHeight;
                rowHeight = 0;
            }
        }
        textureHeight = charY + rowHeight;

        BufferedImage bufferedImage = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, textureWidth, textureHeight);
        graphics2D.setColor(Color.white);

        for (int targetChar = startChar; targetChar < stopChar; targetChar++) {
            if (fontImages[targetChar] != null && charLocations[targetChar] != null) {
                graphics2D.drawImage(fontImages[targetChar], charLocations[targetChar].x, charLocations[targetChar].y,
                        null);
            }
        }

        textureID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), bufferedImage, true,
                true);
    }

    private BufferedImage drawCharToImage(char ch) {
        Graphics2D graphics2D = ((Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics());

        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(font);

        int charWidth = graphics2D.getFontMetrics().charWidth(ch) + 8;
        if (charWidth <= 0)
            charWidth = 7;

        int charHeight = graphics2D.getFontMetrics().getHeight() + 3;
        if (charHeight <= 0)
            charHeight = font.getSize();

        BufferedImage fontImage = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) fontImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(ch), 3, 1 + graphics.getFontMetrics().getAscent());

        return fontImage;
    }

    public int getStringWidth(String text) {
        int width = 0;

        for (char ch : text.toCharArray()) {
            CharLocation fontChar = charLocations[ch < charLocations.length ? ch : '\u0003'];

            if (fontChar != null) {
                width += fontChar.width - 8;
            }
        }

        return width / 2;
    }

    private static class CharLocation {
        private int x;
        private int y;
        private int width;
        private int height;

        public CharLocation(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
