package com.blob.discord.commands;

import com.blob.discord.Core;
import com.blob.discord.listeners.VoiceJoinListener;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VoiceCmds {

    private static VoiceCmds instance = null;
    public static VoiceCmds getInstance() {
        if (instance == null) instance = new VoiceCmds();
        return instance;
    }

    //UserId, ChannelId
    private HashMap<Long, Long> privateAutoVoiceChannels = new HashMap<Long, Long>();
    //ChannelId, Joined
    private HashMap<Long, Boolean> createdAndJoinedPVC = new HashMap<Long, Boolean>();

    //Creates a private VC
    public void createPrivateVoice(Guild guild, Message message, Member member) {
        HashMap<String, ArrayList<Long>> getCurrentAutoVoiceChannels = VoiceJoinListener.getInstance().getCurrentAutoVoiceChannels();
        //Makes sure the user doesn't already have a Private VC
        if (!getInstance().privateAutoVoiceChannels.containsKey(member.getIdLong())) {
            //Sets position for new private VC
            Integer positionToSet = guild.getVoiceChannelById(getCurrentAutoVoiceChannels.get("Yeetus Voice").get(getCurrentAutoVoiceChannels.get("Yeetus Voice").size() - 1)).getPosition() + 2;
            //Creates new private VC
            guild.createVoiceChannel("\uD83D\uDD10 " + member.getEffectiveName() + "'s VC", guild.getCategoryById(new Core().AutoVCCategoryId))
                    .setPosition(positionToSet)
                    .addPermissionOverride(guild.getPublicRole(), EnumSet.of(Permission.VIEW_CHANNEL), EnumSet.of(Permission.VOICE_CONNECT))
                    .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT), null)
                    .queue(voiceChannel -> {
                        Core.getLogger().info("AUTO VC > Successfully created Private user VC (Name: " + voiceChannel.getName() + ", ID: " + voiceChannel.getId());
                        //Adds private VC owner & ID to private VC hashmap
                        getInstance().privateAutoVoiceChannels.put(member.getIdLong(), voiceChannel.getIdLong());
                        getInstance().createdAndJoinedPVC.put(voiceChannel.getIdLong(), false);

                        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!getInstance().createdAndJoinedPVC.get(voiceChannel.getIdLong())) {
                                    voiceChannel.delete().queue();
                                    Core.getLogger().info("AUTO VC > Successfully deleted a Private user VC");
                                }
                            }
                        };
                        scheduler.schedule(runnable, 30, TimeUnit.SECONDS);
                        scheduler.shutdown();

                    });
            message.reply("Successfully created your Private Voice channel").queue();
        } else {
            message.reply("You already have a Private Voice channel!").queue();
        }
    }

    //Adds an invited member to a private VC
    public void inviteUserToPrivateVoice(Guild guild, Message message, Member member, Member invitedMember) {
        //Makes sure the user has a Private VC
        if (getInstance().privateAutoVoiceChannels.containsKey(member.getIdLong())) {
            guild.getVoiceChannelById(getInstance().privateAutoVoiceChannels.get(member.getIdLong())).getManager().
                    putPermissionOverride(invitedMember, EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT), null).queue();
            message.reply("Successfully added " + invitedMember.getEffectiveName() + " to your private Voice channel!").queue();
        } else {
            message.reply("You do not currently have a Private Voice channel!").queue();
        }
    }

    public HashMap<Long, Boolean> getCreatedAndJoinedPVC() { return createdAndJoinedPVC; }
    public HashMap<Long, Long> getPrivateAutoVoiceChannels() { return privateAutoVoiceChannels; }

}
