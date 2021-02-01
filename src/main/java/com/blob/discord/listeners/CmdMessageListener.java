package com.blob.discord.listeners;

import com.blob.discord.Core;
import com.blob.discord.commands.*;
import com.blob.discord.utilities.BlameSebJSONManager;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public class CmdMessageListener extends ListenerAdapter {

    private static List<String> blobotCmds = new ArrayList<String>();
    private HashMap<User, Long> cooldown = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && !event.getAuthor().getId().equals("741780707109765150")) {
            if (new BlameSebJSONManager().readJsonFile()[3] == (Boolean) true) {
                /*if (new BlameSebJSONManager().readJsonFile()[2] == (Boolean) true && !event.getChannel().getId().equals("785623302503661578")) {
                    return;
                } else {*/
                    String message = event.getMessage().getContentRaw().toLowerCase();
                    if (blobotCmds.contains(message)) {
                        if (!cooldown.containsKey(event.getAuthor())) {
                            //Logging
                            Core.getLogger().info(event.getAuthor().getName() + " issued command: " + event.getMessage().getContentRaw());
                            switch (message) {
                                //Blame Seb Commands
                                case "blame seb":
                                    new BlameSebCmds().blameSeb(event.getChannel(), event.getAuthor().getName());
                                    break;
                                case "forgive seb":
                                    new BlameSebCmds().forgiveSeb(event.getChannel(), event.getAuthor().getName());
                                    break;
                                case "is it sebs fault":
                                case "is it sebs fault?":
                                case "is it seb's fault":
                                case "is it seb's fault?":
                                case "iisf":
                                case "iisf?":
                                    new BlameSebCmds().isItSebsFault(event.getChannel());
                                    break;
                                //Fun Commands
                                case "quickmaths":
                                case "quick maths":
                                    new FunCmds().quickMaths(event.getChannel(), event.getMessage());
                                    break;
                                case "cat":
                                    new FunCmds().cat(event.getChannel());
                                    break;
                                case "dog":
                                    new FunCmds().dog(event.getChannel());
                                    break;
                                case "hedgehog":
                                    new FunCmds().hedgehog(event.getChannel());
                                    break;
                                case "groundhog":
                                    new FunCmds().groundhog(event.getChannel());
                                    break;
                                case "ping me":
                                    new FunCmds().pingMe(event.getChannel(), event.getAuthor());
                                    break;
                                //Utility Commands
                                case "blobot help":
                                case "help":
                                    new HelpCmd().blobotHelp(event.getChannel());
                                    break;
                                case "blobot enable":
                                    new UtilityCmds().blobotToggle(event.getChannel(), event.getAuthor(), event.getMember(), true, event.getGuild());
                                    break;
                                case "blobot disable":
                                    new UtilityCmds().blobotToggle(event.getChannel(), event.getAuthor(), event.getMember(), false, event.getGuild());
                                    break;
                                case "blobot restricted":
                                    new UtilityCmds().blobotRestricted(event.getChannel(), event.getMember());
                                    break;
                                //Other Commands
                                case "t leaderboard":
                                    new OtherCmds().tLeaderboard(event.getChannel(), event.getAuthor(), event.getGuild());
                                    break;
                            /*Fire commands:
                                fire start

                                fire blow
                                fire add wood
                                fire wood

                                fire smother

                                fire explore

                                fire points
                             */
                                case "test":

                                    break;
                            }
                            //Cooldown logic
                            cooldown.put(event.getAuthor(), System.currentTimeMillis() + (2 * 1000));
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    cooldown.remove(event.getAuthor());
                                }
                            }, 2000);
                        } else {
                            event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", a little too quick there boomer! Please wait 2 seconds").queue();
                        }
                    //}
                }
            } else {
                if (event.getMessage().getContentRaw().equals("blobot enable")) {
                    Core.getLogger().info(event.getAuthor().getName() + " issued command: " + event.getMessage().getContentRaw());
                    new UtilityCmds().blobotToggle(event.getChannel(), event.getAuthor(), event.getMember(), true, event.getGuild());
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
        blobotCmds.add("blobot enable");
        blobotCmds.add("blobot disable");
        blobotCmds.add("blobot restricted");
        blobotCmds.add("t leaderboard");
        blobotCmds.add("test");
        blobotCmds.add("quick maths");
        blobotCmds.add("quickmaths");
    }

}
