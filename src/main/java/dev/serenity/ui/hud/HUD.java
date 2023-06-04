package dev.serenity.ui.hud;

import dev.serenity.Serenity;
import dev.serenity.module.Module;
import dev.serenity.ui.font.Fonts;
import dev.serenity.utilities.MinecraftInstance;
import dev.serenity.utilities.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HUD extends MinecraftInstance {
    private float hue = 0.0f;

    public void render() {

        ScaledResolution sr = new ScaledResolution(mc);

        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        int color = ColorUtils.getRainbow(4, 0.4f, 1f, 0);
        Fonts.minecraftFont.drawStringWithShadow("S", 3.0f, 3.0f, color);
        Fonts.minecraftFont.drawStringWithShadow("erenity " + EnumChatFormatting.GRAY + "[" + EnumChatFormatting.WHITE + "1.8.x" + EnumChatFormatting.GRAY + "] " + EnumChatFormatting.GRAY + "[" + EnumChatFormatting.WHITE + Minecraft.getDebugFPS() + " FPS" + EnumChatFormatting.GRAY + "]", 3 + Fonts.minecraftFont.getStringWidth("S"), 3.0f, Color.WHITE.getRGB());

        int y = 2;
        final List<Module> modules = new CopyOnWriteArrayList<>();
        for (final Module module : Serenity.getInstance().getModuleManager().getModules()) {
            if (module.isEnabled() && !module.isHidden()) {
                modules.add(module);
            }
        }
        modules.sort((o1, o2) -> Fonts.minecraftFont.getStringWidth(o2.getSuffix() != null ? o2.getName() + " " + o2.getSuffix() : o2.getName()) - Fonts.minecraftFont.getStringWidth(o1.getSuffix() != null ? o1.getName() + " " + o1.getSuffix() : o1.getName()));
        this.hue += 15f / 5.0f;
        if (this.hue > 255.0f) {
            this.hue = 0.0f;
        }
        float h = this.hue;
        for (final Module module : modules) {
            if (h > 255.0f) {
                h = 0.0f;
            }
            final String suffix = (module.getSuffix() != null) ? (" §7" + module.getSuffix()) : "";
            final int x = (sr.getScaledWidth() - Fonts.minecraftFont.getStringWidth(module.getName() + suffix) - 2);
            Fonts.minecraftFont.drawStringWithShadow(module.getName() + suffix, x, y, color);
            h += 9.0f;
            y += 9;
        }
    }
}