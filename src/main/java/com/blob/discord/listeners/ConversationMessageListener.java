package com.blob.discord.listeners;

import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class ConversationMessageListener extends ListenerAdapter {

    Random Random = new Random();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        /*if (event.getAuthor().getIdLong() == Settings.NoPunctuationId && event.getChannel().getIdLong() == Settings.AnnouncementId) return;

                && event.getMessage().getContentRaw().matches("No Punctuation Needed :)|HEY I SEE THAT! No Punctuation Needed :)")
                && Random.nextInt(100) == 1) {
            event.getMessage().reply("hey be quiet boomer").queue();
        }*/

    }

}
