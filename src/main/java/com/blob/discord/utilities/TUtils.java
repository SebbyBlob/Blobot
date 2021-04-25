package com.blob.discord.utilities;

import com.blob.discord.Core;
import com.blob.discord.managers.BlameSebJSONManager;
import com.blob.discord.managers.TDataJSONManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import java.util.*;

public class TUtils {

    private static TUtils instance = null;
    public static TUtils getInstance() {
        if (instance == null) instance = new TUtils();
        return instance;
    }

    public boolean isTLoaded = false;

    //Runs on bot Startup
    public void onStartup(Guild guild) {
        Core.getLogger().info("Commencing #t calculation, temporarily disabling #t");
        /*guild.getTextChannelById(Settings.TChannelId).getManager()
                .putPermissionOverride(guild.getRoleById(Settings.TrustedRoleId), null, EnumSet.of(Permission.MESSAGE_WRITE)).queue();
        guild.getTextChannelById(Settings.TChannelId).getManager()
                .putPermissionOverride(guild.getRoleById(Settings.StaffRoleId), null, EnumSet.of(Permission.MESSAGE_WRITE)).queue();*/
        guild.getTextChannelById(Settings.TChannelId).upsertPermissionOverride(guild.getRoleById(Settings.TrustedRoleId)).deny(Permission.MESSAGE_WRITE).queue();
        guild.getTextChannelById(Settings.TChannelId).upsertPermissionOverride(guild.getRoleById(Settings.StaffRoleId)).deny(Permission.MESSAGE_WRITE).queue();
        //Makes sure the last t sent is not null
        if (!(new BlameSebJSONManager().readJsonFile()[4] == null)) {
            Message lastSentT = Core.getJDA().getTextChannelById(Settings.TChannelId).retrieveMessageById((Long) new BlameSebJSONManager().readJsonFile()[4]).complete();
            readTAfterMsg(lastSentT, lastSentT.getAuthor().getIdLong(), guild);
        } else {
            //Reads all t's in #t
            readAllT(guild);
        }
    }

    //Reads the first 100 t's in #t
    public void readAllT(Guild guild) {
        Core.getJDA().getTextChannelById(Settings.TChannelId).getHistoryFromBeginning(100).queue(messageHistory -> {
                Message msg;
                long lastTAuthorChecker = 0;
                //Runs through the loop 100 times
                for (int i = 0; i < 100; i++) {
                    try {
                        msg = messageHistory.getRetrievedHistory().get(i);
                    } catch (IndexOutOfBoundsException exception) {
                        setLastSentT();
                        getInstance().isTLoaded = true;
                        /*guild.getTextChannelById(Settings.TChannelId).getManager()
                                .putPermissionOverride(guild.getRoleById(Settings.TrustedRoleId), EnumSet.of(Permission.MESSAGE_WRITE), null).queue();
                        guild.getTextChannelById(Settings.TChannelId).getManager()
                                .putPermissionOverride(guild.getRoleById(Settings.StaffRoleId), EnumSet.of(Permission.MESSAGE_WRITE), null).queue();*/
                        guild.getTextChannelById(Settings.TChannelId).upsertPermissionOverride(guild.getRoleById(Settings.TrustedRoleId)).grant(Permission.MESSAGE_WRITE).queue();
                        guild.getTextChannelById(Settings.TChannelId).upsertPermissionOverride(guild.getRoleById(Settings.StaffRoleId)).grant(Permission.MESSAGE_WRITE).queue();
                        Core.getLogger().info("#t Calculation and check complete!");
                        return;
                    }
                    //Makes sure the message is a "t"
                    if (msg.getContentRaw().equals("t")) {
                        if (msg.getAuthor().getIdLong() == lastTAuthorChecker) {
                            //Core.getLogger().info("Duplicate t found in #t, message link: " + msg.getJumpUrl());
                            System.out.println("1: " + msg.getIdLong());
                            final Message msgJump = msg;
                            System.out.println("2: " + msgJump.getIdLong());
                            System.out.println("lastSaidT: " + lastTAuthorChecker);
                            guild.getJDA().openPrivateChannelById(Settings.SebbyUserId).queue(privateChannel -> {
                                privateChannel.sendMessage("Duplicate t found in #t, message link: " + msgJump.getJumpUrl()).queue();
                            });
                        } else {
                            lastTAuthorChecker = msg.getAuthor().getIdLong();
                            new TDataJSONManager().addTDataJsonValue(msg.getAuthor().getId());
                        }
                    } else {
                        Core.getLogger().info("Non-t found in #t, message link: " + msg.getJumpUrl());
                        Core.getJDA().getTextChannelsByName("general", false).get(0).sendMessage("Non-t found in #t, message link: " + msg.getJumpUrl()).queue();
                    }
                    if (i == 99) {
                        readTAfterMsg(messageHistory.getRetrievedHistory().get(0), guild);
                    }
                }
        });
    }

    //Reads the first 100 t's after a certain message
    public void readTAfterMsg(Message messageAfter, Long lastSaidTAuthorId, Guild guild) {
        Core.getJDA().getTextChannelById(Settings.TChannelId).getHistoryAfter(messageAfter, 100).queue(messageHistory -> {
            if (!messageHistory.isEmpty()) {
                Message msg;
                long lastTAuthorChecker = lastSaidTAuthorId;
                //Runs through the loop 100 times
                for (int i = 0; i < 100; i++) {
                    try {
                        msg = messageHistory.getRetrievedHistory().get(i);
                    } catch (IndexOutOfBoundsException exception) {
                        setLastSentT();
                        getInstance().isTLoaded = true;
                        /*guild.getTextChannelById(Settings.TChannelId).getManager()
                                .putPermissionOverride(guild.getRoleById(Settings.TrustedRoleId), EnumSet.of(Permission.MESSAGE_WRITE), null).queue();
                        guild.getTextChannelById(Settings.TChannelId).getManager()
                                .putPermissionOverride(guild.getRoleById(Settings.StaffRoleId), EnumSet.of(Permission.MESSAGE_WRITE), null).queue();*/
                        guild.getTextChannelById(Settings.TChannelId).upsertPermissionOverride(guild.getRoleById(Settings.TrustedRoleId)).grant(Permission.MESSAGE_WRITE).queue();
                        guild.getTextChannelById(Settings.TChannelId).upsertPermissionOverride(guild.getRoleById(Settings.StaffRoleId)).grant(Permission.MESSAGE_WRITE).queue();
                        Core.getLogger().info("#t Calculation and check complete!");
                        return;
                    }
                    //Makes sure the message is a "t"
                    if (msg.getContentRaw().equals("t")) {
                        if (msg.getAuthor().getIdLong() == lastTAuthorChecker) {
                            //Core.getLogger().info("Duplicate t found in #t, message link: " + msg.getJumpUrl());
                            System.out.println("1: " + msg.getIdLong());
                            final Message msgJump = msg;
                            System.out.println("2: " + msgJump.getIdLong());
                            System.out.println("lastSaidT: " + lastTAuthorChecker);
                            guild.getJDA().openPrivateChannelById(Settings.SebbyUserId).queue(privateChannel -> {
                                privateChannel.sendMessage("Duplicate t found in #t, message link: " + msgJump.getJumpUrl()).queue();
                            });
                        } else {
                            lastTAuthorChecker = msg.getAuthor().getIdLong();
                            new TDataJSONManager().addTDataJsonValue(msg.getAuthor().getId());
                        }
                    } else {
                        Core.getLogger().info("Non-t found in #t, message link: " + msg.getJumpUrl());
                        Core.getJDA().getTextChannelsByName("general", false).get(0).sendMessage("Non-t found in #t, message link: " + msg.getJumpUrl()).queue();
                    }
                    if (i == 99) {
                        readTAfterMsg(messageHistory.getRetrievedHistory().get(0), guild);
                    }
                }
            }
        });
    }

    //Sets the last sent t in #t into tdata.json file
    private void setLastSentT() {
        Core.getJDA().getTextChannelById(Settings.TChannelId).getHistory().retrievePast(1)
                .map(messages -> messages.get(0))
                .queue(message -> {
                    new BlameSebJSONManager().setJsonValue(4, message.getIdLong());
                });
    }

    //Sorts the top users who have said "t" in #t into a list
    public List tSorter() {

        HashMap<String, Long> jsonFileHashMap = new HashMap<>();

        //Puts all said "t"'s and their author into the list
        for (Object objKey : new TDataJSONManager().getTDataJsonObject().keySet()) {
            String key = (String) objKey;

            jsonFileHashMap.put(key, new TDataJSONManager().readJsonFile(key));

        }

        List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(jsonFileHashMap.entrySet());
        //Sorts list highest to lowest
        list.sort(new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return list;
    }

    //Gets the total number of t's said in #t
    public Long getTotalTs() {
        long totalTCount = 0;
        for (Object userTs : new TDataJSONManager().getTDataJsonObject().values()) {
            totalTCount = totalTCount + (Long) userTs;
        }
        return totalTCount;
    }

}
