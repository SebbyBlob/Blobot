package com.blob.discord.listeners;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReactionListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        /*if (!event.getReactionEmote().getName().matches(":easter_egg1:|:easter_egg2:|:easter_egg3:|:easter_egg4:")) return;

        event.retrieveUser().queue(user -> {
            if (!(user.getIdLong() == event.getJDA().getSelfUser().getIdLong())) return;

        });*/

    }

}
