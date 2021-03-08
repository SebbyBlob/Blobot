package com.blob.discord.managers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command {


    private List<String> labels;

    public Command(String... labels) {
        this.labels = Arrays.asList(labels);
    }

    public abstract void onCommand(MessageReceivedEvent event);

    public List<String> getLabels() { return labels; }

}
