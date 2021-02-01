package com.blob.discord.commands;

import com.blob.discord.utilities.BlameSebJSONManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;

public class UtilityCmds {

    //Toggle Blobot on/off

    public void blobotToggle(MessageChannel channel, User author, Member member, Boolean onOff, Guild guild) {
        int i = 0;
        for (Role role : member.getRoles()) {
            if ((i == 0) && (role.getName().equals("Owner") || role.getName().equals("Admin"))) {
                i = 1;
                boolean preToggledValue = (boolean) new BlameSebJSONManager().readJsonFile()[3];
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(new Color(103, 120, 194))
                        .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
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
        }
    }

    //Toggle Blobot for use in #Bot-commands only

    public void blobotRestricted(MessageChannel channel, Member member) {
        for (Role role : member.getRoles()) {
            if (role.getName().equals("Owner") || role.getName().equals("Admin")) {
                boolean restrictedState = (boolean) new BlameSebJSONManager().readJsonFile()[2];
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(new Color(103, 120, 194))
                        .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
                if (restrictedState == true) {
                    new BlameSebJSONManager().setJsonValue(2, false);
                    eb.setTitle("Blobot commands are now Allowed in all channels");
                    channel.sendMessage(eb.build()).queue();
                } else {
                    new BlameSebJSONManager().setJsonValue(2, true);
                    eb.setTitle("Blobot commands are now Only allowed in #bot-commands");
                    channel.sendMessage(eb.build()).queue();
                }
            }
        }
    }

}
