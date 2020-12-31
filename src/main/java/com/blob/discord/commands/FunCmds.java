package com.blob.discord.commands;

import com.blob.discord.utilities.CatGenerator;
import com.blob.discord.utilities.ImageGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class FunCmds {

    //Gives you a random Cat image

    public void cat(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Cat :D**")
                .setImage(new CatGenerator().getCat())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Gives a random hedgehog image

    public void hedgehog(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Hedgehog :hedgehog:**")
                .setImage(new ImageGenerator().getHedgehog())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Gives a random groundhog image

    public void groundhog(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Groundhog**")
                .setImage(new ImageGenerator().getGroundhog())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Ping yourself command

    public void pingMe(MessageChannel channel, User author) {
        channel.sendMessage(author.getAsMention() + " ping pong!").queue();
    }

}
