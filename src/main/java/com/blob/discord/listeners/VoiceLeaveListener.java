package com.blob.discord.listeners;

import com.blob.discord.Core;
import com.blob.discord.commands.VoiceCmds;
import com.blob.discord.utilities.VoiceUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VoiceLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = VoiceJoinListener.getInstance().getCurrentAutoVoiceChannels();

        //Guild & Category ID check
        if (event.getChannelLeft().getGuild().getIdLong() == new Core().AutoVCGuildId
                && event.getChannelLeft().getParent() != null
                && event.getChannelLeft().getParent().getIdLong() == new Core().AutoVCCategoryId) {
            //Checks whether the left voice channel is empty
            if (event.getChannelLeft().getMembers().size() == 0) {
                //Checks whether the left voice channel is an automatic voice channel of a certain type
                if (currentAutoVoiceChannels.get("General").contains(event.getChannelLeft().getIdLong())) {
                    removeAutoVoiceChannel("General", event.getGuild(), event.getChannelLeft());
                } else if (currentAutoVoiceChannels.get("Gaming").contains(event.getChannelLeft().getIdLong())) {
                    removeAutoVoiceChannel("Gaming", event.getGuild(), event.getChannelLeft());
                } else if (currentAutoVoiceChannels.get("Yeetus Voice").contains(event.getChannelLeft().getIdLong())) {
                    removeAutoVoiceChannel("Yeetus Voice", event.getGuild(), event.getChannelLeft());
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
        }
    }

    public void removeAutoVoiceChannel(String type, Guild guild, VoiceChannel getChannelLeft) {
        HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = VoiceJoinListener.getInstance().getCurrentAutoVoiceChannels();

        //Checks if the amount of auto voice channels of this type is less than 4
        if (currentAutoVoiceChannels.get(type).size() < 4) {
            guild.getVoiceChannelById(currentAutoVoiceChannels.get(type).get(currentAutoVoiceChannels.get(type).size() - 1)).delete().queue();
            Core.getLogger().info("AUTO VC > Successfully deleted a " + type + " Auto VC");
            currentAutoVoiceChannels.get(type).remove(currentAutoVoiceChannels.get(type).size() - 1);
        } else {
            //Runs if there are 4 auto voice channels of this type
            int emptyVoiceChannels = 0;
            int timesToRemove = 0;
            //Loops through all the voice channel IDs of the specified type of auto voice channels
            for (Long channelId : currentAutoVoiceChannels.get(type)) {
                //Gets the voice channel from the id
                VoiceChannel channel = guild.getVoiceChannelById(channelId);
                //Checks if this VC has 0 users in it
                if (channel.getMembers().size() == 0) {
                    emptyVoiceChannels++;
                }
                //Adds channel to a toBeDeleted list if there is 2 empty auto voice channels
                if (emptyVoiceChannels == 2) {
                    guild.getVoiceChannelById(currentAutoVoiceChannels.get(type).get(currentAutoVoiceChannels.get(type).size() - 1)).delete().queue();
                    Core.getLogger().info("AUTO VC > Successfully deleted a " + type + " Auto VC");
                    timesToRemove ++;
                    emptyVoiceChannels++;
                }
            }
            //Deletes the requested amount of VCs
            while (timesToRemove > 0) {
                currentAutoVoiceChannels.get(type).remove(currentAutoVoiceChannels.get(type).size() - 1);
                timesToRemove = timesToRemove - 1;
            }
        }
        new VoiceUtils().sortAutoVoiceChannels(type, getChannelLeft.getParent(), guild, currentAutoVoiceChannels);
    }
}
