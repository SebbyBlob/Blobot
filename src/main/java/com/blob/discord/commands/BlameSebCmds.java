package com.blob.discord.commands;

import com.blob.discord.managers.BlameSebJSONManager;
import com.blob.discord.managers.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BlameSebCmds extends Command {

    @Override
    protected void Command(@NotNull MessageReceivedEvent event, String label) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase("blame seb")) {
            blameSeb(event);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("forgive seb")) {
            forgiveSeb(event);
        } else if (event.getMessage().getContentRaw().toLowerCase().matches("is it sebs fault|is it sebs fault\\?|is it seb's fault|is it seb's fault\\?|iisf|iisf\\?")) {
            isItSebsFault(event);
        }
    }

    //Blame Seb
    public void blameSeb(MessageReceivedEvent event) {
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(249, 127, 57))
                .setTitle("**" + event.getMember().getEffectiveName() + " has blamed Seb for the " + new BlameSebJSONManager().setJsonValue(0, null) + " time!**");

        //Checks if it is Seb's fault
        String sebsFault = null;
        long blameSeb = (long) new BlameSebJSONManager().readJsonFile()[0];
        long forgiveSeb = (long) new BlameSebJSONManager().readJsonFile()[1];
        if (blameSeb > forgiveSeb) {
            sebsFault = "Yes";
        } else if (blameSeb == forgiveSeb) {
            sebsFault = "No";
        } else if (blameSeb < forgiveSeb) {
            sebsFault = "No";
        }

        eb.addField("Is it Seb's fault?", sebsFault, false)
                .addField("Seb blames:", new BlameSebJSONManager().readJsonFile()[0].toString(), true)
                .addField("Seb forgives:", new BlameSebJSONManager().readJsonFile()[1].toString(), true)
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    //Forgive Seb
    public void forgiveSeb(MessageReceivedEvent event) {
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(249, 127, 57))
                .setTitle("**" + event.getMember().getEffectiveName() + " has forgiven Seb for the " + new BlameSebJSONManager().setJsonValue(1, null) + " time!**");

        //Checks if it is Seb's fault
        String sebsFault = null;
        long blameSeb = (long) new BlameSebJSONManager().readJsonFile()[0];
        long forgiveSeb = (long) new BlameSebJSONManager().readJsonFile()[1];
        if (blameSeb > forgiveSeb) {
            sebsFault = "Yes";
        } else if (blameSeb == forgiveSeb) {
            sebsFault = "No";
        } else if (blameSeb < forgiveSeb) {
            sebsFault = "No";
        }

        eb.addField("Is it Seb's fault?", sebsFault, false)
                .addField("Seb blames:", new BlameSebJSONManager().readJsonFile()[0].toString(), true)
                .addField("Seb forgives:", new BlameSebJSONManager().readJsonFile()[1].toString(), true)
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    //Is it Seb's fault?
    public void isItSebsFault(MessageReceivedEvent event) {
        //Grabs blame/forgive seb data
        long blameSeb = (long) new BlameSebJSONManager().readJsonFile()[0];
        long forgiveSeb = (long) new BlameSebJSONManager().readJsonFile()[1];
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(249, 127, 57))
                .setTitle("**Is it Seb's fault?**")
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");

        if (blameSeb > forgiveSeb) {
            eb.addField("Seb's fault:", "Yes", false)
                    .addField("Seb blames:", new BlameSebJSONManager().readJsonFile()[0].toString(), true)
                    .addField("Seb forgives:", new BlameSebJSONManager().readJsonFile()[1].toString(), true);
        } else if (blameSeb == forgiveSeb) {
            eb.addField("Seb's fault:", "No", false)
                    .addField("Seb blames:", new BlameSebJSONManager().readJsonFile()[0].toString(), true)
                    .addField("Seb forgives:", new BlameSebJSONManager().readJsonFile()[1].toString(), true);
        } else if (blameSeb < forgiveSeb) {
            eb.addField("Seb's fault:", "No", false)
                    .addField("Seb blames:", new BlameSebJSONManager().readJsonFile()[0].toString(), true)
                    .addField("Seb forgives:", new BlameSebJSONManager().readJsonFile()[1].toString(), true);
        }
        event.getChannel().sendMessage(eb.build()).queue();
    }

}
