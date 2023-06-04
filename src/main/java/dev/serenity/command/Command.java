package dev.serenity.command;

import dev.serenity.Serenity;
import dev.serenity.module.Module;
import dev.serenity.utilities.other.ChatUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public List<String> getAlias() {
        return alias;
    }

    private final String name, description, syntax;
    public List<String> alias = new ArrayList<>();

    public Command(String name, String description, String syntax, String[] alias) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.alias = Arrays.asList(alias);
    }

    public void onExecute(String[] args) {

    }
}
