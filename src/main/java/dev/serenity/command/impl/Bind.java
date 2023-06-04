package dev.serenity.command.impl;

import dev.serenity.Serenity;
import dev.serenity.command.Command;
import dev.serenity.module.Module;
import dev.serenity.module.ModuleManager;
import dev.serenity.utilities.other.ChatUtils;
import org.lwjgl.input.Keyboard;

import java.util.Locale;

public class Bind extends Command {
    public Bind() {
        super("Bind", "Binds module to a key", ".bind <module> <key>", new String[]{"bind"});
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length != 2)
        {
            ChatUtils.addMessage("Syntax: " + this.getSyntax());
            return;
        }
        String name = args[0], key = args[1];
        Module module = Serenity.getInstance().getModuleManager().getModule(name);
        if(module == null)
        {
            ChatUtils.addMessage("Can't find module " + name);
            return;
        }
        module.setKey(Keyboard.getKeyIndex(key.toUpperCase()));
        ChatUtils.addMessage("Bound " + module.getName() + " to key " + Keyboard.getKeyName(module.getKey()));
    }
}
