package com.blob.discord.listeners;

import com.blob.discord.Core;
import com.blob.discord.commands.FunCmds;
import com.blob.discord.utilities.BlameSebJSONManager;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MessageListener extends ListenerAdapter {

    private Random random = new Random();
    private int playersAnswered = 1;

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && !event.getAuthor().getId().equals("741780707109765150")) {
            String rawMessage = event.getMessage().getContentRaw();
            //ok boomer response
            if (new BlameSebJSONManager().readJsonFile()[3] == (Boolean) true) {
                String lowerCaseMsg = rawMessage.toLowerCase();
                String[] messageSplit = lowerCaseMsg.split(" ");
                if (messageSplit[0].equals("ok") || messageSplit[0].equals("okay")) {
                    int i = random.nextInt(10);
                    if (i == 1) {
                        Core.getLogger().info(event.getAuthor().getName() + " triggered an ok boomer: " + rawMessage);
                        event.getChannel().sendMessage("ok boomer").queue();
                    }
                }
            }
            //#t protection
            if (event.getChannel().getId().equals("770731649569783829")) {
                if (!rawMessage.equals("t")) {
                    Core.getLogger().info(event.getAuthor().getName() + " sent a non-t in #t: " + rawMessage);
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessage("Hey " + event.getAuthor().getAsMention() + ", " + event.getTextChannel().getAsMention() + " is for t's only you boomer!").queue(message -> {
                        message.delete().queueAfter(3, TimeUnit.SECONDS);
                    });
                } else {
                    event.getTextChannel().getHistory().retrievePast(2)
                            .map(messages -> messages.get(1))
                            .queue(message -> {
                                if (event.getAuthor().getId().equals(message.getAuthor().getId())) {
                                    message.delete().queue();
                                    event.getChannel().sendMessage("Hey " + event.getAuthor().getAsMention() + ", You can't spam t's!").queue(message1 -> {
                                        message1.delete().queueAfter(3, TimeUnit.SECONDS);
                                    });
                                } else {
                                    //add t to count & last t sent
                                    event.getMessage().getId();
                                }
                            });

                }
            }
            //Quick Maths checker
            if (FunCmds.isQuickMathsInProgress() == true) {
                String[] messageSplit = rawMessage.split(" ");
                if (messageSplit.length == 1) {
                    for (int i = 0; i < messageSplit[0].length(); i++) {
                        if (!(messageSplit[0].charAt(i) >= '0' && messageSplit[0].charAt(i) <= '9')) {
                            return;
                        }
                    }
                    System.out.println("message split " + messageSplit[0]);
                    System.out.println("answer " + FunCmds.getAnswer());
                    if (messageSplit[0].equalsIgnoreCase(FunCmds.getAnswer())) {
                        if (playersAnswered == 1) {
                            event.getMessage().reply("**QUICK MATHS:** #1 " + event.getMember().getEffectiveName() + " answered correctly in " + ).queue();
                            playersAnswered = 2;
                        } else if (playersAnswered == 2) {
                            event.getMessage().reply("**QUICK MATHS:** #2 " + event.getMember().getEffectiveName() + " answered correctly in " + ).queue();
                            playersAnswered = 3;
                        } else if (playersAnswered == 3) {
                            FunCmds.setQuickMathsInProgress(false);
                            event.getMessage().reply("**QUICK MATHS:** #3 " + event.getMember().getEffectiveName() + " answered correctly in " + ).queue();
                            playersAnswered = 1;
                        }
                    } else {
                        System.out.println("5");
                        event.getMessage().reply("That is not the right answer! :(").queue();
                    }
                }
            }
        }
    }

}
