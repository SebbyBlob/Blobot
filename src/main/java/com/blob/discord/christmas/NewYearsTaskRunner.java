package com.blob.discord.christmas;

import com.blob.discord.Core;
import com.blob.discord.utilities.CatGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.Duration;
import java.util.TimerTask;

public class NewYearsTaskRunner extends TimerTask {

    public void run() {
        TextChannel channel = new Core().getJDA().getTextChannelById("700416449641971793");

        String message = "**:tada::tada: Happy New Year 2021!!! :tada::tada:**" +
                "\n@everyone Happy new years chums, hopefully 2021 doesn't decide to like" +
                "\ndie instantly on us. Anyways have a great new year and stay safe :tada:" +
                "\n\n*-Written by Sebby & posted automatically by Blobot on New Years eve at midnight*";

        channel.sendMessage(message).queue();
        //channel.addReactionById(, "U+1F389").delay(Duration.ofSeconds(3)).queue();
    }

}
