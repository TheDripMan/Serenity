package dev.serenity.module;

import dev.serenity.command.Command;
import dev.serenity.command.impl.Clientname;
import dev.serenity.command.impl.Toggle;
import dev.serenity.event.impl.ChatEvent;
import dev.serenity.module.impl.combat.Velocity;
import dev.serenity.module.impl.movement.Speed;
import dev.serenity.module.impl.movement.Sprint;
import dev.serenity.module.impl.render.ClickGUI;
import dev.serenity.module.impl.render.Fullbright;
import dev.serenity.module.impl.render.HUD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CommandManager {
    private final List<Command> commands = new ArrayList<>();
    private final String prefix = ".";

    public CommandManager() {
        init();
    }

    public void init() {
        commands.add(new Toggle());
        commands.add(new Clientname());
    }

    public void handle(ChatEvent event) {
        String message = event.getMessage();

        if(!message.startsWith(prefix))
            return;
        event.cancelEvent();
        message = message.substring(prefix.length());

        if(message.split(" ").length > 0) {
            String command = message.split(" ")[0];
            for(Command c : commands) {
                if(c.alias.contains(command)) {
                    c.onExecute(Arrays.copyOfRange(message.split(" "), 1,  message.split(" ").length));
                }
            }
        }
    }
}
