package com.blob.discord.listeners;

import com.blob.discord.managers.TDataJSONManager;
import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class MessageEditListener extends ListenerAdapter {

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        if (event.getMessage().getChannel().getIdLong() != Settings.TChannelId) return;
        event.getMessage().delete().queue();
        event.getMessage().getChannel().sendMessage("Hey " + event.getAuthor().getAsMention() + ", You can't edit messages in #t").queue(message -> {
            message.delete().queueAfter(2, TimeUnit.SECONDS);
        });
        new TDataJSONManager().removeTDataJsonValue(event.getAuthor().getId());
    }

}
