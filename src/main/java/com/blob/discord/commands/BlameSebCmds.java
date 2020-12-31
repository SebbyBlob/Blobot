package com.blob.discord.commands;

import com.blob.discord.utilities.JSONManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class BlameSebCmds {

    //Blame Seb

    public void blameSeb(MessageChannel channel, String name) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(249, 127, 57))
                .setTitle("**" + name + " has blamed Seb for the " + new JSONManager().setJsonValue(0, null) + " time!**");

        String sebsFault = null;
        long blameSeb = (long) new JSONManager().readJsonFile()[0];
        long forgiveSeb = (long) new JSONManager().readJsonFile()[1];
        if (blameSeb > forgiveSeb) {
            sebsFault = "Yes";
        } else if (blameSeb == forgiveSeb) {
            sebsFault = "No";
        } else if (blameSeb < forgiveSeb) {
            sebsFault = "No";
        }

        eb.addField("Is it Seb's fault?", sebsFault, false)
                .addField("Seb blames:", new JSONManager().readJsonFile()[0].toString(), true)
                .addField("Seb forgives:", new JSONManager().readJsonFile()[1].toString(), true)
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Forgive Seb

    public void forgiveSeb(MessageChannel channel, String name) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(249, 127, 57))
                .setTitle("**" + name + " has forgiven Seb for the " + new JSONManager().setJsonValue(1, null) + " time!**");

        String sebsFault = null;
        long blameSeb = (long) new JSONManager().readJsonFile()[0];
        long forgiveSeb = (long) new JSONManager().readJsonFile()[1];
        if (blameSeb > forgiveSeb) {
            sebsFault = "Yes";
        } else if (blameSeb == forgiveSeb) {
            sebsFault = "No";
        } else if (blameSeb < forgiveSeb) {
            sebsFault = "No";
        }

        eb.addField("Is it Seb's fault?", sebsFault, false)
                .addField("Seb blames:", new JSONManager().readJsonFile()[0].toString(), true)
                .addField("Seb forgives:", new JSONManager().readJsonFile()[1].toString(), true)
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Is it Seb's fault?

    public void isItSebsFault(MessageChannel channel) {
        long blameSeb = (long) new JSONManager().readJsonFile()[0];
        long forgiveSeb = (long) new JSONManager().readJsonFile()[1];
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(249, 127, 57))
                .setTitle("**Is it Seb's fault?**")
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");

        if (blameSeb > forgiveSeb) {
            eb.addField("Seb's fault:", "Yes", false)
                    .addField("Seb blames:", new JSONManager().readJsonFile()[0].toString(), true)
                    .addField("Seb forgives:", new JSONManager().readJsonFile()[1].toString(), true);
        } else if (blameSeb == forgiveSeb) {
            eb.addField("Seb's fault:", "No", false)
                    .addField("Seb blames:", new JSONManager().readJsonFile()[0].toString(), true)
                    .addField("Seb forgives:", new JSONManager().readJsonFile()[1].toString(), true);
        } else if (blameSeb < forgiveSeb) {
            eb.addField("Seb's fault:", "No", false)
                    .addField("Seb blames:", new JSONManager().readJsonFile()[0].toString(), true)
                    .addField("Seb forgives:", new JSONManager().readJsonFile()[1].toString(), true);
        }
        channel.sendMessage(eb.build()).queue();
    }

}
