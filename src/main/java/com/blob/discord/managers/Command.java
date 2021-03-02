package com.blob.discord.managers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public abstract class Command {


    private String label;

    protected void Command(@NotNull MessageReceivedEvent event, String label) {
        this.label = label;
    }

}
