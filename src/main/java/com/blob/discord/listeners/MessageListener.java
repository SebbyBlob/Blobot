package com.blob.discord.listeners;

import com.blob.discord.Core;
import com.blob.discord.commands.FunCmds;
import com.blob.discord.managers.BlameSebJSONManager;
import com.blob.discord.managers.TDataJSONManager;
import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MessageListener extends ListenerAdapter {

    private final Random Random = new Random();
    private int usersAnswered = 1;
    private boolean checkForConfirmAlert = false;
    private List<Long> usersCompleted = new ArrayList<Long>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && !event.getAuthor().getId().equals("741780707109765150")) {
            String rawMessage = event.getMessage().getContentRaw();

            //1 in 10 chance of replying with "ok boomer" when someone says "ok" or variant
            if (new BlameSebJSONManager().readJsonFile()[3] == (Boolean) true) {
                String lowerCaseMsg = rawMessage.toLowerCase();
                String[] messageSplit = lowerCaseMsg.split(" ");
                //Checks if message is equal to "ok" or variant
                if (messageSplit[0].equals("ok") || messageSplit[0].equals("okay")) {
                    int i = Random.nextInt(10);
                    if (i == 1) {
                        Core.getLogger().info(event.getAuthor().getName() + " triggered an ok boomer: " + rawMessage);
                        event.getMessage().reply("ok boomer").queue();
                    }
                }
            }

            //#t protection
            if (event.getChannel().getId().equals("770731649569783829")) {
                //Checks if message is not equal to "t"
                if (!rawMessage.equals("t")) {
                    Core.getLogger().info(event.getAuthor().getName() + " sent a non-t in #t: " + rawMessage);
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessage("Hey " + event.getAuthor().getAsMention() + ", " + event.getTextChannel().getAsMention() + " is for t's only you boomer!").queue(message -> {
                        message.delete().queueAfter(3, TimeUnit.SECONDS);
                    });
                } else {
                    //Makes sure user don't send two t's in a row
                    event.getTextChannel().getHistory().retrievePast(2)
                            .map(messages -> messages.get(1))
                            .queue(message -> {
                                if (event.getAuthor().getId().equals(message.getAuthor().getId())) {
                                    message.delete().queue();
                                    event.getChannel().sendMessage("Hey " + event.getAuthor().getAsMention() + ", You can't spam t's!").queue(message1 -> {
                                        message1.delete().queueAfter(3, TimeUnit.SECONDS);
                                    });
                                } else {
                                    new TDataJSONManager().addTDataJsonValue(event.getAuthor().getId());
                                    new BlameSebJSONManager().setJsonValue(4, event.getMessage().getIdLong());
                                }
                            });

                }
            }

            //Announcement #general Ping
            if (event.getMessage().getChannel().getIdLong() == Settings.AnnouncementId) {
                event.getGuild().getTextChannelById(Settings.NuclearTestingAreaId)
                        .sendMessage(event.getGuild().getMemberById(Settings.SebbyUserId).getAsMention() + " send announcement alert to #general? Please confirm by typing `ConfirM` within 12 hours").queue();

                checkForConfirmAlert = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        checkForConfirmAlert = false;
                    }
                }, (1000 * 60 * 60 * 12));
            }
            //Check if Confirm Announce Alert command
            if (checkForConfirmAlert == true
                    && event.getMessage().getChannel().getIdLong() == Settings.NuclearTestingAreaId
                    && event.getMessage().getAuthor().getIdLong() == Settings.SebbyUserId
                    && event.getMessage().getContentRaw().equals("ConfirM")) {
                //todo: cancel timer task
                event.getMessage().reply("Sending Announcement alert to #general").queue();
                for (int i=0; i < 5; i++) {
                    event.getGuild().getTextChannelById(Settings.GeneralChatId).sendMessage(":exclamation::exclamation: :fire: #\uD83D\uDCE2announcements :fire: :exclamation::exclamation:").queue();
                }
            }

            //Quick Maths checker
            if (FunCmds.isQuickMathsInProgress() == true) {
                //Checks if channel is the quick maths channel
                if (event.getChannel().equals(FunCmds.getChannelStarted())) {
                    String[] messageSplit = rawMessage.split(" ");
                    if (messageSplit.length == 1) {
                        //Makes sure all characters in message are digits
                        for (int i = 0; i < messageSplit[0].length(); i++) {
                            if (!(messageSplit[0].charAt(i) >= '0' && messageSplit[0].charAt(i) <= '9')) {
                                return;
                            }
                        }
                        //Makes sure user isn't answering twice
                        if (!usersCompleted.contains(event.getAuthor().getIdLong())) {
                            if (messageSplit[0].equalsIgnoreCase(FunCmds.getAnswer())) {
                                event.getMessage().delete().queue();
                                int elapsedTimeSinceCreation = (int) ((System.currentTimeMillis() - FunCmds.getCreationTime()) / 10);
                                String displaySeconds = "" + elapsedTimeSinceCreation;
                                displaySeconds = displaySeconds.substring(0, displaySeconds.length()-2) + "." + displaySeconds.substring(displaySeconds.length()-1);
                                if (usersAnswered == 1) {
                                    event.getChannel().sendMessage("**QUICK MATHS:** :first_place: " + event.getMember().getAsMention() + " answered correctly in " + displaySeconds + " seconds").queue();
                                    usersCompleted.add(event.getAuthor().getIdLong());
                                    usersAnswered = 2;
                                } else if (usersAnswered == 2) {
                                    event.getChannel().sendMessage("**QUICK MATHS:** :second_place: " + event.getMember().getAsMention() + " answered correctly in " + displaySeconds + " seconds").queue();
                                    usersCompleted.add(event.getAuthor().getIdLong());
                                    usersAnswered = 3;
                                } else if (usersAnswered == 3) {
                                    FunCmds.setQuickMathsInProgress(false);
                                    event.getChannel().sendMessage("**QUICK MATHS:** :third_place: " + event.getMember().getAsMention() + " answered correctly in " + displaySeconds + " seconds").queue();
                                    event.getChannel().sendMessage("**QUICK MATHS OVER:** " + FunCmds.getEquation() + " = " + FunCmds.getAnswer()).queue();
                                    usersCompleted.clear();
                                    usersAnswered = 1;
                                }
                            } else {
                                event.getMessage().reply("That is not the right answer! :(").queue();
                            }
                        } else {
                            event.getMessage().delete().queue();
                            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " You can't answer again!").queue(message -> {
                                message.delete().queueAfter(3, TimeUnit.SECONDS);
                            });;
                        }
                    }
                }
            }
        }
    }

    public void setUsersAnswered(int usersAnswered) { this.usersAnswered = usersAnswered; }
    public List<Long> getUsersCompleted() { return usersCompleted; }

}
