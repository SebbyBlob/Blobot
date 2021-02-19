package com.blob.discord.commands;

import com.blob.discord.utilities.TDataJSONManager;
import com.blob.discord.utilities.TUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class OtherCmds {

    //T Leaderboard Command
    public void tLeaderboard(MessageChannel channel, User user, Guild guild) {
        List<Map.Entry<String, Long>> sortedList = new TUtils().tSorter();
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(248, 139, 78))
                .setTitle("**:regional_indicator_t:   #t Leaderboard   :regional_indicator_t:**")
                .setDescription("\n" +
                        "\n:first_place: **" + guild.retrieveMemberById(sortedList.get(0).getKey()).complete().getEffectiveName() + " - " + sortedList.get(0).getValue() + " t's**" +
                        "\n:second_place: **" + guild.retrieveMemberById(sortedList.get(1).getKey()).complete().getEffectiveName() + " - " + sortedList.get(1).getValue() + " t's**" +
                        "\n:third_place: **" + guild.retrieveMemberById(sortedList.get(2).getKey()).complete().getEffectiveName() + " - " + sortedList.get(2).getValue() + " t's**" +
                        "\n:four: " + guild.retrieveMemberById(sortedList.get(3).getKey()).complete().getEffectiveName() + " - " + sortedList.get(3).getValue() + " t's" +
                        "\n:five: " + guild.retrieveMemberById(sortedList.get(4).getKey()).complete().getEffectiveName() + " - " + sortedList.get(4).getValue() + " t's" +
                        "\n" +
                        "\nYou have said **" + new TDataJSONManager().readJsonFile(user.getId()) + "** t's in #t" +
                        "\n" +
                        "\n*Only the top 5 users are shown*")
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

}
