package com.blob.discord.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUserJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (event.getGuild().getIdLong() == 530904600119869450L) {
            event.getGuild().getSystemChannel().sendMessage(event.getMember().getAsMention() + " To Gain Access to the rest of TGU please post who invited you (or how you joined) in this channel!").queue();
        }
    }

}
