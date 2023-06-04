package dev.serenity.command.impl;

import dev.serenity.Serenity;
import dev.serenity.command.Command;
import dev.serenity.module.Module;
import dev.serenity.utilities.other.ChatUtils;

public class Clientname extends Command {
    public Clientname() {
        super("Client Name", "Changes client's display name", ".clientname <name>", new String[]{"clientname"});
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length <= 0) return;
        String name = "";
        for(String s : args) {
            name += s + " ";
        }
        name = name.substring(0,name.length()-1);
        Serenity.getInstance().setName(name);
    }
}
