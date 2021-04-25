package com.blob.discord.listeners;

import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConversationMessageListener extends ListenerAdapter {

    Random random = new Random();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() != Settings.NoPunctuationId) return;
        if (event.getChannel().getIdLong() == Settings.AnnouncementId) return;
        switch (event.getMessage().getContentRaw().toLowerCase()) {
            case "No Punctuation Needed :)":
            case "HEY I SEE THAT! No Punctuation Needed :)":
                if (random.nextInt(51) != 1) return;
                event.getChannel().sendTyping().queue();
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                Runnable task = (Runnable) executor.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getMessage().reply("hey be quiet boomer").queue();
                    }
                }, 4, TimeUnit.SECONDS);
                break;
        }
    }

}
