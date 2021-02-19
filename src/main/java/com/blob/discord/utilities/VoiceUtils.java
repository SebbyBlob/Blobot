package com.blob.discord.utilities;

import com.blob.discord.Core;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.HashMap;

public class VoiceUtils {

    public void sortAutoVoiceChannels(String type, Category category, Guild guild, HashMap<String, ArrayList<Long>> currentAutoVoiceChannels) {
        if (guild == null) return;

        int i = 1;
        Long emptyChannelId = null;
        VoiceChannel firstChannelOfType = guild.getVoiceChannelById(currentAutoVoiceChannels.get(type).get(0));

        //Loops through all voice channels in the category
        for (VoiceChannel channel : category.getVoiceChannels()) {
            //Checks if the channel is an Auto VC of certain type
            if (currentAutoVoiceChannels.get(type).contains(channel.getIdLong())) {
                //Deletes voice channels if 2 or more of type of auto VC are empty
                if (channel.getMembers().size() == 0) {
                    if (emptyChannelId != null) {
                        //Checks if the emptyChannel to be deleted is the original voice channel of type
                        if (emptyChannelId.equals(firstChannelOfType.getIdLong())) {
                            channel.delete().queue();
                            Core.getLogger().info("AUTO VC > Successfully deleted a " + type + " Auto VC");
                            currentAutoVoiceChannels.get(type).remove(channel.getIdLong());
                        } else {
                            guild.getVoiceChannelById(emptyChannelId).delete().queue();
                            currentAutoVoiceChannels.get(type).remove(emptyChannelId);
                            emptyChannelId = channel.getIdLong();
                        }
                    } else {
                        emptyChannelId = channel.getIdLong();
                    }
                }
                //Makes sure the channel is not the first channel of this type
                if ((!currentAutoVoiceChannels.get(type).get(0).equals(channel.getIdLong())) && !(i == 1)) {
                    //Checks if the type is Yeetus Voice
                    int getChanneNum = 2;
                    if (type == "Yeetus Voice") getChanneNum = 3;
                    String[] splitName = channel.getName().split(" ");
                    //Checks if the channel position is in the incorrect position
                    if (channel.getPosition() != firstChannelOfType.getPosition() + (i - 1)) {
                        //Sets the position to move the channel to
                        int moveToPosition = guild.getVoiceChannelById(currentAutoVoiceChannels.get(type).get(0)).getPosition() + (i - 1);
                        //Makes sure the position is valid
                        if (moveToPosition + 1 < category.getVoiceChannels().size()) {
                            //Moves the channel to specified position
                            category.modifyVoiceChannelPositions().selectPosition(channel).moveTo(moveToPosition).queue();
                        }
                    }
                    //Checks if the channel name number is incorrect
                    if (!splitName[getChanneNum].equals(String.valueOf(i))) {
                        if (type.equals("General")) {
                            channel.getManager().setName("\uD83C\uDFA4 General " + i).queue();
                        } else if (type.equals("Gaming")) {
                            channel.getManager().setName("\uD83C\uDFAE Gaming " + i).queue();
                        } else if (type.equals("Yeetus Voice")) {
                            channel.getManager().setName("⭐ Yeetus Voice " + i).queue();
                        }
                    }
                }
                i++;
            }
        }
    }

}
