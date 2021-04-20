package com.blob.discord.listeners;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUserLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        TextChannel channel = event.getGuild().getTextChannelsByName("general", true).get(0);
        String[] splitTag = event.getUser().getAsTag().split("#");
        channel.sendMessage("**" + splitTag[0] + " #" + splitTag[1] + "** left the server! :slight_frown:").queue();
    }

}
