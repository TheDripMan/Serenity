package dev.serenity.module;

import dev.serenity.event.impl.*;
import dev.serenity.setting.Setting;
import dev.serenity.utilities.MinecraftInstance;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class Module extends MinecraftInstance {
    private final String name;
    private final String description;
    private final Category category;
    private int key;
    private boolean state;
    private boolean hidden = false;
    public ArrayList<Setting> settings = new ArrayList<>();
    public boolean expanded = false;
    public ResourceLocation arrow = new ResourceLocation("serenity/clickgui/arrow.png");
    public float height = 0F;
    public float settingHeight = 0F;

    public Module(String name, String description, Category category, int key, boolean enabled) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.key = key;
        this.state = enabled;
    }

    public void toggle() {
        state = !state;
        if (state) onEnable();
        else onDisable();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getSuffix() {
        return null;
    }

    public void onEnable() {}
    public void onDisable() {}

    public void onUpdate(final UpdateEvent event) {}
    public void onRender2D(final Render2DEvent event) {}
    public void onPreMotion(final PreMotionEvent event) {}
    public void onPostMotion(final PostMotionEvent event) {}
    public void onPacket(final PacketEvent event) {}
    public void onSlowDown(final SlowDownEvent event) {}
    public void onTick(final TickEvent event) {}
    public void onStrafe(final StrafeEvent event) {}
    public void onAttack(final AttackEvent event) {}
}
