package com.blob.discord.commands;

import com.blob.discord.listeners.VoiceJoinListener;
import com.blob.discord.managers.BlameSebJSONManager;
import com.blob.discord.managers.Command;
import com.blob.discord.utilities.RoleUtils;
import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class UtilityCmds extends Command {

    public UtilityCmds() {
        super("blobot enable", "blobot disable", "blobot restricted", "vcreset", "vc reset", "t disable", "t enable", "!announce ping", "!announceping");
    }

    @Override
    public void onCommand(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot enable")) {
            blobotToggle(event, true);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot disable")) {
            blobotToggle(event, false);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot restricted")) {
            blobotRestricted(event);
        } else if (event.getMessage().getContentRaw().toLowerCase().matches("vcreset|vc reset")) {
            resetAutoVc(event);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("t enable")) {
            toggleT(event, true);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("t disable")) {
            toggleT(event, false);
        } else if (event.getMessage().getContentRaw().toLowerCase().matches("!announce ping|!announceping")) {
            announcePing(event);
        }
    }

    //Toggle Blobot on/off
    public void blobotToggle(MessageReceivedEvent event, Boolean onOff) {
        if (!new RoleUtils().hasRole(event.getMember(), "Owner", event.getGuild())) {
            event.getMessage().reply(Settings.NoPermissionMessage).queue(message1 -> {
                event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });
            return;
        }
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
        event.getMessage().getChannel().sendMessage(eb.build()).queue();
    }

    //Toggle Blobot for use in #Bot-commands only
    private void blobotRestricted(MessageReceivedEvent event) {
        if (!new RoleUtils().hasRole(event.getMember(), "Owner", event.getGuild())) {
            event.getMessage().reply(Settings.NoPermissionMessage).queue(message1 -> {
                event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });
            return;
        }
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
        event.getMessage().reply(eb.build()).queue();
    }

    //Toggles #t send-messages permission
    private void toggleT(MessageReceivedEvent event, boolean onOff) {
        if (!new RoleUtils().hasRole(event.getMember(), "Owner", event.getGuild())) {
            event.getMessage().reply(Settings.NoPermissionMessage).queue(message1 -> {
                event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });
            return;
        }
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(103, 120, 194))
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        //Sets #t permissions based on new state
        if (onOff == true) {
            event.getGuild().getTextChannelById(Settings.TChannelId).putPermissionOverride(event.getGuild().getRoleById(Settings.TrustedRoleId)).setAllow(Permission.MESSAGE_WRITE).queue();
            event.getGuild().getTextChannelById(Settings.TChannelId).putPermissionOverride(event.getGuild().getRoleById(Settings.StaffRoleId)).setAllow(Permission.MESSAGE_WRITE).queue();
            eb.setTitle("#t is now Enabled");
        } else {
            event.getGuild().getTextChannelById(Settings.TChannelId).putPermissionOverride(event.getGuild().getRoleById(Settings.TrustedRoleId)).deny(Permission.MESSAGE_WRITE).queue();
            event.getGuild().getTextChannelById(Settings.TChannelId).putPermissionOverride(event.getGuild().getRoleById(Settings.StaffRoleId)).deny(Permission.MESSAGE_WRITE).queue();
            eb.setTitle("#t is now Disabled");
        }
        event.getMessage().reply(eb.build()).queue();
    }

    //Resets all automatic VCs
    private void resetAutoVc(MessageReceivedEvent event) {
        if (!new RoleUtils().hasRole(event.getMember(), "Owner", event.getGuild())) {
            event.getMessage().reply(Settings.NoPermissionMessage).queue(message1 -> {
                event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });
            return;
        }
        HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = VoiceJoinListener.getInstance().getCurrentAutoVoiceChannels();
        //Loops through all the lists within currentAutoVoiceChannels
        for (ArrayList<Long> list : currentAutoVoiceChannels.values()) {
            //Loops through all the channel IDs in the list
            for (Long channelId : list) {
                //Makes sure the channel is not the original VC of type
                if (!channelId.equals(list.get(0))) {
                    event.getGuild().getVoiceChannelById(channelId).delete().queue();
                    list.remove(channelId);
                }
            }
        }
        event.getMessage().reply("Successfully reset all automatic voice channels!").queue();
    }

    private void announcePing(MessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() != Settings.SebbyUserId) {
            event.getMessage().reply(Settings.NoPermissionMessage).queue();
            return;
        }
        event.getMessage().delete().queue();
        for (int i=0; i < 5; i++) {
            event.getGuild().getTextChannelById(Settings.GeneralChatId)
                    .sendMessage(":exclamation::exclamation: :fire: " + event.getGuild().getTextChannelById(Settings.AnnouncementId).getAsMention() + " :fire: :exclamation::exclamation:").queue();
        }
    }

}
