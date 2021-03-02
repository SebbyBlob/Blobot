package com.blob.discord.managers;

import com.blob.discord.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {

    private static CommandManager instance = null;
    public static CommandManager getInstance() {
        if (instance == null) instance = new CommandManager();
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

    public void registerCommand(Command command) {
        getInstance().getCommands().add(command);
    }

    public List<Command> getCommands() { return commands; }

}
