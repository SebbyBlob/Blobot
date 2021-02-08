package com.blob.discord.listeners;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class VoiceLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        HashMap<String, ArrayList<Long>> getCurrentAutoVoiceChannels = new VoiceJoinListener().getCurrentAutoVoiceChannels();
        if (event.getChannelLeft().getParent() != null && event.getChannelLeft().getParent().getId().equals("")) {
            if (event.getChannelLeft().getMembers().size() == 0) {
                if (getCurrentAutoVoiceChannels.get("General").contains(event.getChannelLeft().getIdLong())) {
                    event.getGuild().getVoiceChannelById(getCurrentAutoVoiceChannels.get("General").get(getCurrentAutoVoiceChannels.size() - 1)).delete().queue();
                }
            }
        }

    }

}
