package com.blob.discord.managers;

import com.blob.discord.commands.*;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private CommandManager() {
    }
    private static final CommandManager instance = new CommandManager();
    private List<Command> commands = new ArrayList<>();

    public static CommandManager getInstance() {
        return instance;
    }

    public void onStartup() {
        registerCommand(new BlameSebCmds());
        registerCommand(new FunCmds());
        registerCommand(new HelpCmd());
        registerCommand(new OtherCmds());
        registerCommand(new UtilityCmds());
    }

    public boolean hasCommandWithLabel(String message) {
        for (Command cmdClass : commands) {
            for (String label : cmdClass.getLabels()) {
                if (message.equalsIgnoreCase(label)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Command getCommandByLabel(String message) {
        for (Command cmdClass : getInstance().commands) {
            if (cmdClass.getLabels().contains(message)) {
                return cmdClass;
            }
        }
        return null;
    }

    public void registerCommand(Command command) {
        getInstance().getCommands().add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

}
