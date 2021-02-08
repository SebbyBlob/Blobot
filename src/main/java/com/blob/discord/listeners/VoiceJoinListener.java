package com.blob.discord.listeners;

import com.blob.discord.Core;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VoiceJoinListener extends ListenerAdapter {

    /*
    Check if joined a channel with users already in, if not checks whether they joined original channel or empty temp channel
     */

    private HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = new HashMap<String, ArrayList<Long>>();
    //private HashMap<String, ArrayList<Long>> currentAutoVoiceChannels = new HashMap<String, ArrayList<Long>>();
    //private List<String> general = new ArrayList<String>();

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        System.out.println("event:");
        System.out.println(event.getChannelJoined().getIdLong());
        System.out.println(currentAutoVoiceChannels);

        //Category ID check
        /*if (event.getGuild().getIdLong() == 541279761906008069L && event.getChannelJoined().getParent().getId().equals("806386393328386100")) {
            //General, Gaming, & Yeetus Voice ID check
            System.out.println("test");
            if (general
            .contains(
                    event.getChannelJoined()
                    .getId()
            )) {
                System.out.println("Complete!");
            }*/
        if (currentAutoVoiceChannels
                .get("General")
                .contains(
                        event.getChannelJoined()
                                .getIdLong())) {
            System.out.println("test1");
            if (event.getChannelJoined().getMembers().size() == 1) {
                if (currentAutoVoiceChannels.get("General").get(0).equals(event.getChannelJoined().getIdLong())) {
                    event.getGuild().createVoiceChannel("\uD83C\uDFA4 General 2", event.getChannelJoined().getParent()).setPosition(event.getChannelJoined().getPosition() + 1).queue();
                } else {
                    if (currentAutoVoiceChannels.get("General").size() < 4) {
                        event.getGuild().createVoiceChannel("\uD83C\uDFA4 General " + currentAutoVoiceChannels.get("General").size() + 1, event.getChannelJoined().getParent()).setPosition(event.getChannelJoined().getPosition() + 1).queue();
                    }
                }
            } else {

            }
        } else if (currentAutoVoiceChannels.get("Gaming").contains(event.getChannelJoined().getIdLong())) {
            if (event.getChannelJoined().getMembers().size() == 1) {

            } else {

            }
        } else if (currentAutoVoiceChannels.get("Yeetus Voice").contains(event.getChannelJoined().getIdLong())) {
            if (event.getChannelJoined().getMembers().size() == 1) {

            } else {

            }
        }


        /*for (VoiceChannel channel : event.getChannelJoined().getParent().getVoiceChannels()) {
            String[] split = channel.getName().split(" ");

        }*/

    }

    public void onStartup() {
        Guild guild = Core.getJDA().getGuildById(541279761906008069L);
        if (guild == null) {
            Core.getLogger().error("Guild is null in VoiceJoinListener.onStartup");
            return;
        }

        ArrayList<Long> general = new ArrayList<Long>();
        ArrayList<Long> gaming = new ArrayList<Long>();
        ArrayList<Long> yeetusVoice = new ArrayList<Long>();

        try {
            //general.add(guild.getVoiceChannelsByName("\uD83C\uDFA4 General", false).get(0).getId());
            general.add(guild.getVoiceChannelsByName("\uD83C\uDFA4 General", false).get(0).getIdLong());
            gaming.add(guild.getVoiceChannelsByName("\uD83C\uDFAE Gaming", false).get(0).getIdLong());
            yeetusVoice.add(guild.getVoiceChannelsByName("⭐ Yeetus Voice", false).get(0).getIdLong());
        } catch (NullPointerException e) {
            Core.getLogger().error("There are no original Voice Channels: VoiceJoinListener.onStartup()");
        }

        System.out.println("e: " + Arrays.toString(general.toArray()));

        currentAutoVoiceChannels.put("General", general);
        currentAutoVoiceChannels.put("Gaming", gaming);
        currentAutoVoiceChannels.put("Yeetus Voice", yeetusVoice);

        for (String key : currentAutoVoiceChannels.keySet()) {
            System.out.println("key: " + key);
        }

        System.out.println("values:");
        for (List valueList : currentAutoVoiceChannels.values()) {
            valueList.forEach(System.out::println);
        }

        System.out.println("hashmap:");
        System.out.println(currentAutoVoiceChannels);

        /*System.out.println(currentAutoVoiceChannels.values());
        if (currentAutoVoiceChannels.containsKey("General")) {
            System.out.println("contains key General");
        }*/

        /*for (VoiceChannel channel : Core.getJDA().getCategoryById("806386393328386100").getVoiceChannels()) {
            String[] splitName = channel.getName().split(" ");
            if (splitName[1].equals("General")) {
                if (splitName[2].equals("2") || splitName[2].equals("3") || splitName[2].equals("4")) {
                    if (Core.getJDA() && channel.getMembers().size() == 0) {

                    }
                }
            } else if (splitName[1].equals("Gaming")) {

            } else if (splitName[1].equals("Yeetus Voice")) {

            }
        }*/
    }

    public HashMap<String, ArrayList<Long>> getCurrentAutoVoiceChannels() {
        return currentAutoVoiceChannels;
    }

    public void setCurrentAutoVoiceChannels(HashMap<String, ArrayList<Long>> currentAutoVoiceChannels) {
        this.currentAutoVoiceChannels = currentAutoVoiceChannels;
    }

}
