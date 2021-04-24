package com.blob.discord.listeners;

import com.blob.discord.Core;
import com.blob.discord.commands.UtilityCmds;
import com.blob.discord.commands.VoiceCmds;
import com.blob.discord.managers.BlameSebJSONManager;
import com.blob.discord.managers.CommandManager;
import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CmdMessageListener extends ListenerAdapter {

    private static List<String> blobotCmds = new ArrayList<String>();
    private HashMap<User, Long> cooldown = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()
                && event.isFromType(ChannelType.TEXT)
                && !event.getAuthor().getId().equals("741780707109765150")) {
            if (new BlameSebJSONManager().readJsonFile()[3] == (Boolean) true) {
                if (new BlameSebJSONManager().readJsonFile()[2] == (Boolean) true && event.getChannel().getIdLong() != Settings.BotCmdsId) return;
                String message = event.getMessage().getContentRaw().toLowerCase();
                String[] messageSplit = message.split(" ");
                if (blobotCmds.contains(message)) {
                    if (!cooldown.containsKey(event.getAuthor())) {
                        //Logging
                        Core.getLogger().info(event.getAuthor().getName() + " issued command: " + event.getMessage().getContentRaw());

                        //Command running
                        if (CommandManager.getInstance().hasCommandWithLabel(message)) {
                            CommandManager.getInstance().getCommandByLabel(message).onCommand(event);
                        }

                    } else {
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", a little too quick there! Please wait 1.5 seconds").queue(sentMessage -> {
                            sentMessage.delete().queueAfter(3, TimeUnit.SECONDS);
                        });
                    }
                    //Cooldown logic
                    cooldown.put(event.getAuthor(), System.currentTimeMillis() + (1500));
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            cooldown.remove(event.getAuthor());
                        }
                    }, 1500);
                } else {
                    //Private VC Commands
                    if (messageSplit.length == 1 && messageSplit[0].equalsIgnoreCase("vccreate")) {
                        new VoiceCmds().createPrivateVoice(event);
                    } else if (messageSplit.length == 2 && messageSplit[0].equalsIgnoreCase("vcinvite")) {
                        String userId = messageSplit[1].substring(3, messageSplit[1].length() - 1);
                        try {
                            event.getGuild().retrieveMemberById(userId).queue(member -> {
                                if (event.getGuild().getMemberById(userId) != null) {
                                    new VoiceCmds().inviteUserToPrivateVoice(event, member);
                                } else {
                                    event.getMessage().reply("The provided user is invalid! Cmd format: vcinvite " + event.getGuild().getSelfMember().getAsMention()).queue();
                                }
                            });
                        } catch (IllegalArgumentException exception) {
                            event.getMessage().reply("The provided user is invalid! Cmd format: vcinvite " + event.getGuild().getSelfMember().getAsMention()).queue();
                        }
                    }
                    //Pronouns command
                    else if (messageSplit.length == 2 && messageSplit[0].toLowerCase().matches("pronouns|/pronouns|pronoun|/pronoun")) {
                        //Gets the user ID from the raw message string
                        String userId = messageSplit[1].substring(3, messageSplit[1].length() - 1);
                        try {
                            //Attempts to retrieve the member using the ID
                            event.getGuild().retrieveMemberById(userId).queue(member -> {
                                if (event.getGuild().getMemberById(userId) != null) {
                                    //Creates a new embed
                                    EmbedBuilder eb = new EmbedBuilder()
                                            .setColor(new Color(249, 127, 57))
                                            .setTitle(member.getEffectiveName() + "'s pronouns")
                                            .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");

                                    //Creates a new StringBuilder
                                    StringBuilder stringBuilder = new StringBuilder();
                                    //Checks if the specified member had any pronouns and adds them to StringBuilder
                                    for (Role role : member.getRoles()) {
                                        if (role.getName().toLowerCase().matches("she/her|he/him|they/them|xe/xem|ve/ver|ey/em")) {
                                            stringBuilder.append(role.getName() + ", ");
                                        }
                                    }

                                    //Checks if any pronouns were added and builds the String
                                    if (stringBuilder.length() != 0) {
                                        //Removes the last 2 characters of the built StringBuilder to remove the ", "
                                        String pronouns = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 2);
                                        eb.setDescription(pronouns);
                                    } else {
                                        eb.setDescription("None");
                                    }
                                    //Sends the message with embed
                                    event.getMessage().reply(eb.build()).queue();
                                } else {
                                    event.getMessage().reply("The provided user is invalid! Cmd format: vcinvite " + event.getGuild().getSelfMember().getAsMention()).queue();
                                }
                            });
                        } catch (IllegalArgumentException exception) {
                            event.getMessage().reply("The provided user is invalid! Cmd format: vcinvite " + event.getGuild().getSelfMember().getAsMention()).queue();
                        }
                    }
                }
            } else {
                if (event.getMessage().getContentRaw().equals("blobot enable")) {
                    Core.getLogger().info(event.getAuthor().getName() + " issued command: " + event.getMessage().getContentRaw());
                    new UtilityCmds().blobotToggle(event, true);
                }
            }
        } else if (event.isFromType(ChannelType.PRIVATE) && !event.getAuthor().getId().equals("741780707109765150")) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " *This is an automated Bot and therefore this message will not be seen, Sorry!*").queue();
        }
    }

    public void onStartup() {
        blobotCmds.add("blame seb");
        blobotCmds.add("forgive seb");
        blobotCmds.add("is it sebs fault");
        blobotCmds.add("is it sebs fault?");
        blobotCmds.add("is it seb's fault");
        blobotCmds.add("is it seb's fault?");
        blobotCmds.add("iisf");
        blobotCmds.add("iisf?");
        blobotCmds.add("cat");
        blobotCmds.add("dog");
        blobotCmds.add("hedgehog");
        blobotCmds.add("groundhog");
        blobotCmds.add("ping me");
        blobotCmds.add("blobot help");
        blobotCmds.add("help");
        blobotCmds.add("staff help");
        blobotCmds.add("staffhelp");
        blobotCmds.add("blobot enable");
        blobotCmds.add("blobot disable");
        blobotCmds.add("blobot restricted");
        blobotCmds.add("t leaderboard");
        blobotCmds.add("tleaderboard");
        blobotCmds.add("quick maths");
        blobotCmds.add("quickmaths");
        blobotCmds.add("quickmaths end");
        blobotCmds.add("quick maths end");
        blobotCmds.add("quickmaths stop");
        blobotCmds.add("quick maths stop");
        blobotCmds.add("vcreset");
        blobotCmds.add("vc reset");
        blobotCmds.add("amogus");
        blobotCmds.add("!announce ping");
        blobotCmds.add("!announceping");
        blobotCmds.add("t enable");
        blobotCmds.add("t disable");
    }

}
