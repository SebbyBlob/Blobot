package com.blob.discord.commands;

import com.blob.discord.Core;
import com.blob.discord.listeners.VoiceJoinListener;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

public class VoiceCmds {

    private HashMap<Long, Long> privateAutoVoiceChannels = new HashMap<Long, Long>();

    public void createPrivateVoice(Guild guild, Member member) {
        HashMap<String, ArrayList<Long>> getCurrentAutoVoiceChannels = new VoiceJoinListener().getCurrentAutoVoiceChannels();
        Integer positionToSet = guild.getVoiceChannelById(getCurrentAutoVoiceChannels.get("Yeetus Voice").get(getCurrentAutoVoiceChannels.size() - 1)).getPosition() + 1;

        Long privateVoiceId = guild.createVoiceChannel("\uD83D\uDD10 " + member.getEffectiveName() + "'s Private Voice", Core.getJDA().getCategoryById(806386393328386100L))
                .setPosition(positionToSet)
                .addPermissionOverride(guild.getRoleById("541279761906008069"), null, EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT))
                .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT), null)
                .complete().getIdLong();


    }

    public void inviteUserToPrivateVoice(Guild guild, Message message, Member member, Member invitedMember) {
        if (privateAutoVoiceChannels.containsKey(member.getIdLong())) {
            guild.getVoiceChannelById(privateAutoVoiceChannels.get(member.getIdLong())).getManager().
                    putPermissionOverride(invitedMember, EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT), null).queue();
            message.reply("Successfully added " + invitedMember.getEffectiveName() + " to your private Voice channel!").queue();
        } else {
            message.reply("You do not currently have a Private Voice channel!").queue();
        }
    }

}
