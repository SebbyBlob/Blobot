package com.blob.discord.listeners;

import com.blob.discord.Core;
import com.blob.discord.commands.VoiceCmds;
import com.blob.discord.utilities.VoiceUtils;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VoiceMoveListener extends ListenerAdapter {

    //FIXED: Glitch when a 2nd user in General moves to General 2

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = VoiceJoinListener.getInstance().getCurrentAutoVoiceChannels();

        //Guild & Category ID check
        if (event.getChannelLeft().getGuild().getIdLong() == new Core().AutoVCGuildId) {

            //Checks whether left & joined vc are the same auto VC type
            String channelLeftTypeShort = null;
            String channelJoinedTypeShort = null;
            if (event.getChannelLeft().getParent() != null
                    && event.getChannelLeft().getParent().getIdLong() == new Core().AutoVCCategoryId
                    && event.getChannelLeft().getName().split(" ").length >= 2 && event.getChannelLeft().getName().split(" ")[1].matches("General|Gaming|Yeetus"))
                channelLeftTypeShort = event.getChannelLeft().getName().split(" ")[1];
            if (event.getChannelJoined().getParent() != null
                    && event.getChannelJoined().getParent().getIdLong() == new Core().AutoVCCategoryId
                    && event.getChannelJoined().getName().split(" ").length >= 2 && event.getChannelJoined().getName().split(" ")[1].matches("General|Gaming|Yeetus"))
                channelJoinedTypeShort = event.getChannelJoined().getName().split(" ")[1];

            if (channelLeftTypeShort == null || !channelLeftTypeShort.equals(channelJoinedTypeShort)) {

                //Voice channel leaving
                if (event.getChannelLeft().getParent() != null
                        && event.getChannelLeft().getParent().getIdLong() == new Core().AutoVCCategoryId
                        && event.getChannelLeft().getMembers().size() == 0) {
                    //Checks whether the left voice channel is an automatic voice channel of a certain type
                    if (currentAutoVoiceChannels.get("General").contains(event.getChannelLeft().getIdLong())) {
                        new VoiceLeaveListener().removeAutoVoiceChannel("General", event.getGuild(), event.getChannelLeft());
                    } else if (currentAutoVoiceChannels.get("Gaming").contains(event.getChannelLeft().getIdLong())) {
                        new VoiceLeaveListener().removeAutoVoiceChannel("Gaming", event.getGuild(), event.getChannelLeft());
                    } else if (currentAutoVoiceChannels.get("Yeetus Voice").contains(event.getChannelLeft().getIdLong())) {
                        new VoiceLeaveListener().removeAutoVoiceChannel("Yeetus Voice", event.getGuild(), event.getChannelLeft());
                    } else if (VoiceCmds.getInstance().getPrivateAutoVoiceChannels().containsValue(event.getChannelLeft().getIdLong())) {
                        VoiceCmds.getInstance().getCreatedAndJoinedPVC().replace(event.getChannelLeft().getIdLong(), false);

                        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!VoiceCmds.getInstance().getCreatedAndJoinedPVC().get(event.getChannelLeft().getIdLong())) {
                                    event.getChannelLeft().delete().queue();
                                    Core.getLogger().info("AUTO VC > Successfully deleted a Private user VC");
                                }
                            }
                        };
                        scheduler.schedule(runnable, 30, TimeUnit.SECONDS);
                        scheduler.shutdown();
                    }
                }

                //Voice Channel joining
                if (event.getChannelJoined().getParent() != null
                        && event.getChannelJoined().getParent().getIdLong() == new Core().AutoVCCategoryId
                        && event.getChannelJoined().getMembers().size() == 1) {
                    //Checks whether the joined channel is an automatic voice channel of a certain type
                    if (currentAutoVoiceChannels.get("General").contains(event.getChannelJoined().getIdLong())) {
                        new VoiceJoinListener().createAutoVoiceChannel(true, "General", "\uD83C\uDFA4 General ", 8, event.getGuild(), event.getChannelJoined());
                    } else if (currentAutoVoiceChannels.get("Gaming").contains(event.getChannelJoined().getIdLong())) {
                        new VoiceJoinListener().createAutoVoiceChannel(true, "Gaming", "\uD83C\uDFAE Gaming ", 6, event.getGuild(), event.getChannelJoined());
                    } else if (currentAutoVoiceChannels.get("Yeetus Voice").contains(event.getChannelJoined().getIdLong())) {
                        new VoiceJoinListener().createAutoVoiceChannel(true, "Yeetus Voice", "⭐ Yeetus Voice ", 4, event.getGuild(), event.getChannelJoined());
                    } else if (VoiceCmds.getInstance().getPrivateAutoVoiceChannels().containsValue(event.getChannelJoined().getIdLong())) {
                        VoiceCmds.getInstance().getCreatedAndJoinedPVC().replace(event.getChannelJoined().getIdLong(), true);
                    }
                }

            }

            //Sorts voice channels if the channels are an Auto VC of any type
            if (channelLeftTypeShort != null && channelLeftTypeShort.matches("General|Gaming|Yeetus")) {
                if (channelLeftTypeShort.equals("Yeetus")) channelLeftTypeShort = "Yeetus Voice";
                new VoiceUtils().sortAutoVoiceChannels(channelLeftTypeShort, event.getChannelLeft().getParent(), event.getGuild(), currentAutoVoiceChannels);
            }
            if (channelJoinedTypeShort != null && channelJoinedTypeShort.matches("General|Gaming|Yeetus")) {
                if (channelJoinedTypeShort.equals("Yeetus")) channelJoinedTypeShort = "Yeetus Voice";
                new VoiceUtils().sortAutoVoiceChannels(channelJoinedTypeShort, event.getChannelJoined().getParent(), event.getGuild(), currentAutoVoiceChannels);
            }
        }
    }

}
