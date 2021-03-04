package com.blob.discord.managers;

import com.blob.discord.commands.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {

    private CommandManager() {
    }
    private static final CommandManager instance = new CommandManager();
    public static CommandManager getInstance() {
        return instance;
    }

    private List<Command> commands = new ArrayList<>();

    public void onStartup() {
        registerCommand(new BlameSebCmds());
        registerCommand(new FunCmds());
        registerCommand(new HelpCmd());
        registerCommand(new OtherCmds());
        registerCommand(new UtilityCmds());
    }

    public boolean hasCommandWithLabel(String message) {
        for (Command cmdClass : commands) {
            if (message.equalsIgnoreCase(cmdClass.getLabel())) {
                return true;
            }
        }
        return false;
    }

    public Command getCommandByLabel(String label) {
        for (Command cmdClass : commands) {
            if (cmdClass.getLabel().equalsIgnoreCase(label)) {
                return cmdClass;
            }
        }
        return null;
    }

    public void registerCommand(Command command) {
        getInstance().getCommands().add(command);
    }

    public List<Command> getCommands() { return commands; }

}
