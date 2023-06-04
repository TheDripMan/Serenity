package dev.serenity.command.impl;

import dev.serenity.Serenity;
import dev.serenity.command.Command;
import dev.serenity.module.Module;
import dev.serenity.utilities.other.ChatUtils;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", "Toggles a module", ".toggle <name>", new String[]{"toggle", "t"});
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length <= 0) return;
        String module = args[0];
        Module m = Serenity.getInstance().getModuleManager().getModule(module);
        if(m == null) {
            ChatUtils.addMessage("Cant find module " + module);
            return;
        }
        Serenity.getInstance().getModuleManager().getModule(module).toggle();
        ChatUtils.addMessage( (m.isEnabled() ? "Enabled" : "Disabled") + " " + module);
    }
}
