package com.blob.discord.listeners;

import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GuildUserJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (event.getGuild().getIdLong() == Settings.AutoVCGuildId) {
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    event.getGuild().getSystemChannel().sendMessage(event.getMember().getAsMention() + " To Gain Access to the rest of TGU please post who invited you (or how you joined) in this channel!").queue();
                }
            };
            scheduler.schedule(runnable, 500, TimeUnit.MILLISECONDS);
            scheduler.shutdown();
        }
    }

}
