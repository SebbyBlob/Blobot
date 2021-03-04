package com.blob.discord.commands;

import com.blob.discord.managers.Command;
import com.blob.discord.utilities.RoleUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HelpCmd extends Command {

    public HelpCmd() {
        super("help");
    }

    @Override
    public void onCommand(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().matches("blobot help|help")) {
            blobotHelp(event);
        } else if (event.getMessage().getContentRaw().toLowerCase().matches("staff help|staffhelp")) {
            staffHelp(event);
        }
    }

    //Help Command
    public void blobotHelp(MessageReceivedEvent event) {
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(64, 158, 230))
                .setTitle("**Blobot Help**")
                .setDescription("" +
                        "_All commands are case insensitive meaning you can use either lowercase or uppercase letters_" +
                        "\n\n__Blame Seb Commands__:" +
                        "\n`blame seb` **-** Blames seb once" +
                        "\n`forgive seb` **-** Forgives seb once" +
                        "\n`is it sebs fault` [aliases: `is it sebs fault?`, `is it seb's fault`, `is it seb's fault?`, `iisf`, `iisf?`] **-** Checks if its Seb's fault or not" +
                        "\n\n__Fun Commands__:" +
                        "\n`cat` **-** Gives you a random cat image" +
                        "\n`dog` **-** Gives you a random dog image" +
                        "\n`hedgehog` **-** Gives you a random hedgehog image" +
                        "\n`groundhog` **-** Gives you a random groundhog image" +
                        "\n`ping me` **-** Pings you" +
                        "\n\n__Voice Commands__:" +
                        "\n`vccreate` **-** Creates a Private VC only you can join" +
                        "\n`vcinvite @User` **-** Invites users to your Private VC" +
                        "\n\n__Other Commands__:" +
                        "\n`t leaderboard` **-** Shows a top 5 leaderboard of who has the most t's in #t")
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    public void staffHelp(MessageReceivedEvent event) {
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(64, 158, 230))
                .setTitle("**Blobot Help**")
                .setDescription("" +
                        "_All commands are case insensitive meaning you can use either lowercase or uppercase letters_" +
                        "\n\n__Administration Commands__:" +
                        "\n`blobot enable` **-** Enables Blobot if Blobot disabled" +
                        "\n`blobot disable` **-** Disables Blobot if Blobot enabled" +
                        "\n`blobot restricted` **-** Toggles Blobot cmds for use only in #bot-commands" +
                        "\n`vcreset`/`vc reset` **-** Resets all automatic voice channels" +
                        "\n\n__Fun Commands__:" +
                        "\n`quickmaths` **-** Starts a Quickmaths game" +
                        "\n`quickmaths end` **-** Stops any current Quickmaths game")
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

}
