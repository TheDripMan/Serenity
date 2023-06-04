package dev.serenity.event;

import dev.serenity.Serenity;
import dev.serenity.event.impl.*;
import dev.serenity.module.Module;

import java.util.ArrayList;

public class EventHandler {
    private static final ArrayList<Module> modules = Serenity.getInstance().getModuleManager().getModules();

    public static void handle(final Event e) {

        if (e instanceof UpdateEvent) {
            UpdateEvent event = (UpdateEvent) e;

            for (Module module : modules) {
                if (module.isEnabled()) {
                    module.onUpdate(event);
                }
            }
        } else if (e instanceof KeyEvent) {
            KeyEvent event = (KeyEvent) e;

            for (Module module : modules) {
                if (module.getKey() == event.getKey()) {
                    module.toggle();
                }
            }
        } else if (e instanceof Render2DEvent) {
            Render2DEvent event = (Render2DEvent) e;

            for (Module module : modules) {
                if (module.isEnabled()) {
                    module.onRender2D(event);
                }
            }
        } else if (e instanceof PreMotionEvent) {
            final PreMotionEvent event = ((PreMotionEvent) e);

            for (final Module module : modules) {
                if (module.isEnabled()) {
                    module.onPreMotion(event);
                }
            }
        } else if (e instanceof PostMotionEvent) {
            final PostMotionEvent event = ((PostMotionEvent) e);

            for (final Module module : modules) {
                if (module.isEnabled()) {
                    module.onPostMotion(event);
                }
            }
        } else if (e instanceof PacketEvent) {
            final PacketEvent event = ((PacketEvent) e);

            for (final Module module : modules) {
                if (module.isEnabled()) {
                    module.onPacket(event);
                }
            }
        } else if (e instanceof  ChatEvent) {
            final ChatEvent event = ((ChatEvent) e);
            Serenity.getInstance().getCommandManager().handle(event);
        }
    }
}
