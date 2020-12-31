package com.blob.discord.commands;

import com.blob.discord.utilities.ImageGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class HelpCmd {

    //Help Command

    public void blobotHelp(MessageChannel channel) {

        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(64, 158, 230))
                .setTitle("**Blobot Help**")
                .setDescription("\n_Blame Seb Commands_:" +
                        "\n`blame seb` **-** Blames seb once" +
                        "\n`forgive seb` **-** Forgives seb once" +
                        "\n`is it sebs fault` [aliases: `is it sebs fault?`, `is it seb's fault`, `is it seb`s fault?`, `iisf`, `iisf?`] **-** Checks if its Seb\'s fault or not" +
                        "\n\n_Fun Commands_:" +
                        "\n`cat` **-** Gives you a random cat image" +
                        "\n`hedgehog` **-** Gives you a random hedgehog image" +
                        "\n`groundhog` **-** Gives you a random groundhog image")
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();

    }

}
