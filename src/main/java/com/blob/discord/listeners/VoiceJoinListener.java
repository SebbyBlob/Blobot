package com.blob.discord.listeners;

import com.blob.discord.Core;
import com.blob.discord.commands.VoiceCmds;
import com.blob.discord.utilities.VoiceUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public class VoiceJoinListener extends ListenerAdapter {

    private static VoiceJoinListener instance = null;
    private final HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = new HashMap<String, ArrayList<Long>>();

    public static VoiceJoinListener getInstance() {
        if (instance == null) instance = new VoiceJoinListener();
        return instance;
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = getInstance().getCurrentAutoVoiceChannels();
        //Guild & Category ID check
        if (event.getChannelJoined().getGuild().getIdLong() == new Core().AutoVCGuildId
                && event.getChannelJoined().getParent() != null
                && event.getChannelJoined().getParent().getIdLong() == new Core().AutoVCCategoryId) {
            //Checks whether the joined voice channel was previously empty
            if (event.getChannelJoined().getMembers().size() == 1) {
                //Checks whether the joined channel is an automatic voice channel of a certain type
                if (currentAutoVoiceChannels.get("General").contains(event.getChannelJoined().getIdLong())) {
                    createAutoVoiceChannel("General", "\uD83C\uDFA4 General ", 8, event.getGuild(), event.getChannelJoined());
                } else if (currentAutoVoiceChannels.get("Gaming").contains(event.getChannelJoined().getIdLong())) {
                    createAutoVoiceChannel("Gaming", "\uD83C\uDFAE Gaming ", 6, event.getGuild(), event.getChannelJoined());
                } else if (currentAutoVoiceChannels.get("Yeetus Voice").contains(event.getChannelJoined().getIdLong())) {
                    createAutoVoiceChannel("Yeetus Voice", "⭐ Yeetus Voice ", 4, event.getGuild(), event.getChannelJoined());
                } else if (VoiceCmds.getInstance().getPrivateAutoVoiceChannels().containsValue(event.getChannelJoined().getIdLong())) {
                    VoiceCmds.getInstance().getCreatedAndJoinedPVC().replace(event.getChannelJoined().getIdLong(), true);
                }
            }
        }
    }

    public void createAutoVoiceChannel(String type, String name, Integer userLimit, Guild guild, VoiceChannel getChannelJoined) { createAutoVoiceChannel(false, type, name, userLimit, guild, getChannelJoined); }
    public void createAutoVoiceChannel(boolean moveEvent, String type, String name, Integer userLimit, Guild guild, VoiceChannel getChannelJoined) {
        HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = getInstance().getCurrentAutoVoiceChannels();
        //Checks whether the amount of auto voice channels of this type is less than 4
        if (currentAutoVoiceChannels.get(type).size() < 4) {
            //Creates a new auto voice channel, sets the category & user limit
            guild.createVoiceChannel(name + (currentAutoVoiceChannels.get(type).size() + 1), getChannelJoined.getParent())
                    .setUserlimit(userLimit).queue(voiceChannel -> {
                Core.getLogger().info("AUTO VC > Successfully created a " + type + " Auto VC (Name: " + voiceChannel.getName() + ", ID: " + voiceChannel.getId() + ")");
                //Adds the created voice channel to auto voice channel list
                currentAutoVoiceChannels.get(type).add(voiceChannel.getIdLong());
                //Sets permissions if the channel is Gaming/Yeetus Voice
                if (type.equals("Gaming")) {
                    voiceChannel.getManager().putPermissionOverride(guild.getPublicRole(), EnumSet.of(Permission.VIEW_CHANNEL), EnumSet.of(Permission.VOICE_CONNECT))
                            .putPermissionOverride(guild.getRolesByName("Trusted", false).get(0), EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT), null)
                            .queue();
                } else if (type.equals("Yeetus Voice")) {
                    voiceChannel.getManager().putPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT))
                            .putPermissionOverride(guild.getRolesByName("super secret stuff", false).get(0), EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT), null)
                            .queue();
                }

                List<VoiceChannel> parentCategoryVoiceChannels = new ArrayList<>(getChannelJoined.getParent().getVoiceChannels());
                if ((getChannelJoined.getPosition() + 1) < guild.getVoiceChannelById(parentCategoryVoiceChannels.get(parentCategoryVoiceChannels.size() - 1).getIdLong()).getPosition()) {
                    guild.modifyVoiceChannelPositions().selectPosition(voiceChannel).moveTo(getChannelJoined.getPosition() + 1).queue();
                }
                new VoiceUtils().sortAutoVoiceChannels(type, getChannelJoined.getParent(), guild, currentAutoVoiceChannels);
            });
        }
    }

    public void onStartup() {
        Guild guild = Core.getJDA().getGuildById(new Core().AutoVCGuildId);
        if (guild == null) {
            Core.getLogger().error("Guild is null in VoiceJoinListener.onStartup");
            return;
        }

        ArrayList<Long> general = new ArrayList<Long>();
        ArrayList<Long> gaming = new ArrayList<Long>();
        ArrayList<Long> yeetusVoice = new ArrayList<Long>();

        //Adds default type voice channels to auto VC lists
        HashMap<Integer, List<VoiceChannel>> typeVCs = new HashMap<Integer, List<VoiceChannel>>();
        typeVCs.put(1, guild.getVoiceChannelsByName("\uD83C\uDFA4 General", false));
        typeVCs.put(2, guild.getVoiceChannelsByName("\uD83C\uDFAE Gaming", false));
        typeVCs.put(3, guild.getVoiceChannelsByName("⭐ Yeetus Voice", false));
        Integer[] typeOriginalChannels = {0, 0, 0};
        Long[] originalVCsId = {0L, 0L, 0L};
        int iterationLoop = 0;
        for (List<VoiceChannel> typeChannelList : typeVCs.values()) {
            for (VoiceChannel channel : typeChannelList) {
                if (channel.getParent() != null && channel.getParent().getIdLong() == new Core().AutoVCCategoryId) {
                    typeOriginalChannels[iterationLoop] ++;
                    originalVCsId[iterationLoop] = channel.getIdLong();
                }
            }
            if (typeOriginalChannels[iterationLoop] != 1) {
                Core.getLogger().error("There are to many/not enough original Voice Channels: VoiceJoinListener.onStartup()");
            }
            iterationLoop++;
        }

        general.add(originalVCsId[0]);
        gaming.add(originalVCsId[1]);
        yeetusVoice.add(originalVCsId[2]);

        //Assigns auto VC lists to auto VC hashmap
        getInstance().getCurrentAutoVoiceChannels().put("General", general);
        getInstance().getCurrentAutoVoiceChannels().put("Gaming", gaming);
        getInstance().getCurrentAutoVoiceChannels().put("Yeetus Voice", yeetusVoice);

        //Checks if their are more than the default type VCs
        HashMap<String, ArrayList<Long>> autoTypeVoiceChannels = new HashMap<String, ArrayList<Long>>();
        autoTypeVoiceChannels.put("General", new ArrayList<Long>());
        autoTypeVoiceChannels.put("Gaming", new ArrayList<Long>());
        autoTypeVoiceChannels.put("Yeetus Voice", new ArrayList<Long>());

        for (VoiceChannel channel : guild.getCategoryById(new Core().AutoVCCategoryId).getVoiceChannels()) {
            String[] splitName = channel.getName().split(" ");
            String type = null;
            //Checks if the channel is an auto VC of type and assigns type
            if (splitName[1].equals("General")) {
                type = "General";
            } else if (splitName[1].equals("Gaming")) {
                type = "Gaming";
            } else if (splitName[1].equals("Yeetus")) {
                type = "Yeetus Voice";
            }
            if (type != null) {
                if (type.equals("Yeetus Voice") && splitName.length == 4 || !type.equals("Yeetus Voice") && splitName.length == 3) {
                    if (splitName[2].matches("2|3|4")) {
                        //Checks if the original type channel AND the channel have no users joined OR if there are more than 2 empty VC's of that type
                        if (guild.getVoiceChannelById(getInstance().getCurrentAutoVoiceChannels().get(type).get(0)).getMembers().size() == 0 && channel.getMembers().size() == 0) {
                            channel.delete().queue();
                        } else if (autoTypeVoiceChannels.get(type).size() >= 2) {
                            int channelsEmpty = 0;
                            for (Long channelId : autoTypeVoiceChannels.get(type)) {
                                VoiceChannel voiceChannel = guild.getVoiceChannelById(channelId);
                                if (voiceChannel.getMembers().size() == 0) channelsEmpty++;
                                if (channelsEmpty >= 2) {
                                    channel.delete().queue();
                                    channelsEmpty = channelsEmpty - 1;
                                }
                            }
                        } else {
                            autoTypeVoiceChannels.get(type).add(channel.getIdLong());
                            getInstance().getCurrentAutoVoiceChannels().get(type).add(channel.getIdLong());
                        }
                    }
                }
            }
        }
        Core.getLogger().info("Current Auto Voice Channels: " + getInstance().getCurrentAutoVoiceChannels());
        autoTypeVoiceChannels.clear();
    }

    public HashMap<String, ArrayList<Long>> getCurrentAutoVoiceChannels() {
        return currentAutoVoiceChannels;
    }

}
