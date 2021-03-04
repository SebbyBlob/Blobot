package com.blob.discord.managers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public abstract class Command {


    private String label;

    public Command(String label) {
        this.label = label;
    }

    public abstract void onCommand(MessageReceivedEvent event);

    public String getLabel() { return label; }

}
