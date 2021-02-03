package com.blob.discord.listeners;

import com.blob.discord.Core;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class VoiceJoinListener extends ListenerAdapter {

    /*
    Checks all
     */

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        //Category ID check
        if (event.getChannelJoined().getParent().getId().equals("806386393328386100")) {
            //General, Gaming, & Yeetus Voice ID check
            if (event.getChannelJoined().getId().equals("806386438617956363")) {
                event.getChannelJoined().getNa
            } else if (event.getChannelJoined().getId().equals("806386474177790002")) {

            } else if (event.getChannelJoined().getId().equals("806386545111203860")) {

            }
        }

        for (VoiceChannel channel : event.getChannelJoined().getParent().getVoiceChannels()) {
            String[] split = channel.getName().split(" ");

        }

    }

    public void onStartup() {
        Core.getJDA().getVoiceChannelsByName("\uD83C\uDFA4 General", false).get(0)
        for (VoiceChannel channel : Core.getJDA().getCategoryById("").getVoiceChannels()) {

        }
    }

}
