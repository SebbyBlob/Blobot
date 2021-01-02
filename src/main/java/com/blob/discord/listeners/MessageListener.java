package com.blob.discord.listeners;

import com.blob.discord.utilities.JSONManager;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && !event.getAuthor().getId().equals("741780707109765150")) {
            if (new JSONManager().readJsonFile()[3] == (Boolean) true) {
                String rawMessage = event.getMessage().getContentRaw().toLowerCase();
                String[] messageSplit = rawMessage.split(" ");
                if (messageSplit[0].equals("ok")) {
                    event.getChannel().sendMessage("ok boomer").queue();
                }
            }
        }
    }

}
