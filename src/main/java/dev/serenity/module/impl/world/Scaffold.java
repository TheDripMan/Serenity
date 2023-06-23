package dev.serenity.module.impl.world;

import dev.serenity.module.Category;
import dev.serenity.module.Module;
import org.lwjgl.input.Keyboard;

public class Scaffold extends Module {
    public Scaffold() {
        super("Scaffold", "Automatically places blocks beneath your feet.", Category.WORLD, Keyboard.KEY_F, false);
    }
}
