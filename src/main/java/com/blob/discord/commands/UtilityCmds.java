package com.blob.discord.commands;

import com.blob.discord.listeners.VoiceJoinListener;
import com.blob.discord.utilities.BlameSebJSONManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UtilityCmds {

    //Toggle Blobot on/off
    public void blobotToggle(MessageChannel channel, Boolean onOff) {
        //Gets the current toggle value
        boolean preToggledValue = (boolean) new BlameSebJSONManager().readJsonFile()[3];
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(103, 120, 194))
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        //Sets Embed description/fields depending on new toggle status
        if (onOff == true && preToggledValue == false) {
            new BlameSebJSONManager().setJsonValue(3, onOff);
            eb.setTitle("**Blobot is now Enabled**")
                    .setDescription("Type \"blobot disable\" if needed to disable blobot again.")
                    .addField("Blobot Status", "Enabled", true)
                    .addField("Restricted Mode", new BlameSebJSONManager().readJsonFile()[2].toString(), true);
        } else if (onOff == false && preToggledValue == true) {
            new BlameSebJSONManager().setJsonValue(3, onOff);
            eb.setTitle("**Blobot is now Disabled**")
                    .setDescription("Type \"blobot enable\" if needed to enable blobot again.")
                    .addField("Blobot Status", "Disabled", true)
                    .addField("Restricted Mode", new BlameSebJSONManager().readJsonFile()[2].toString(), true);
        } else {
            if (preToggledValue == true) {
                eb.setTitle("**Blobot is already Enabled**")
                        .setDescription("Type \"blobot disable\" if needed to disable blobot.")
                        .addField("Blobot Status", "Enabled", true)
                        .addField("Restricted Mode", new BlameSebJSONManager().readJsonFile()[2].toString(), true);
            } else {
                eb.setTitle("**Blobot is already Disabled**")
                        .setDescription("Type \"blobot enable\" if needed to enable blobot.")
                        .addField("Blobot Status", "Disabled", true)
                        .addField("Restricted Mode", new BlameSebJSONManager().readJsonFile()[2].toString(), true);
            }
        }
        channel.sendMessage(eb.build()).queue();
    }

    //Toggle Blobot for use in #Bot-commands only
    public void blobotRestricted(MessageChannel channel) {
        //Gets current restricted state
        boolean restrictedState = (boolean) new BlameSebJSONManager().readJsonFile()[2];
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(103, 120, 194))
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        //Sets fields based on new state
        if (restrictedState == true) {
            new BlameSebJSONManager().setJsonValue(2, false);
            eb.setTitle("Blobot commands are now Allowed in all channels");
        } else {
            new BlameSebJSONManager().setJsonValue(2, true);
            eb.setTitle("Blobot commands are now Only allowed in #bot-commands");
        }
        channel.sendMessage(eb.build()).queue();
    }

    //Resets all automatic VCs
    public void resetAutoVc(Guild guild, Message message) {
        HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = VoiceJoinListener.getInstance().getCurrentAutoVoiceChannels();
        //Loops through all the lists within currentAutoVoiceChannels
        for (ArrayList<Long> list : currentAutoVoiceChannels.values()) {
            //Loops through all the channel IDs in the list
            for (Long channelId : list) {
                //Makes sure the channel is not the original VC of type
                if (!channelId.equals(list.get(0))) {
                    guild.getVoiceChannelById(channelId).delete().queue();
                    list.remove(channelId);
                }
            }
        }
        message.reply("Successfully reset all automatic voice channels!").queue();
    }

}
