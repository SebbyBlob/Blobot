package com.blob.discord.commands;

import com.blob.discord.managers.Command;
import com.blob.discord.managers.TDataJSONManager;
import com.blob.discord.utilities.TUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class OtherCmds extends Command {

    public OtherCmds() {
        super("tleaderboard", "t leaderboard", "amogus");
    }

    @Override
    public void onCommand(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().matches("t leaderboard|tleaderboard")) {
            tLeaderboard(event);
        } else if (event.getMessage().getContentRaw().toLowerCase().matches("amogus")) {
            amogusEasterEggCmd(event);
        }
    }

    //T Leaderboard Command
    public void tLeaderboard(MessageReceivedEvent event) {
        List<Map.Entry<String, Long>> sortedList = new TUtils().tSorter();
        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(248, 139, 78))
                .setTitle("**:regional_indicator_t:   #t Leaderboard   :regional_indicator_t:**")
                .setDescription("\n" +
                        "\n:first_place: **" + event.getGuild().retrieveMemberById(sortedList.get(0).getKey()).complete().getEffectiveName() + " - " + sortedList.get(0).getValue() + " t's**" +
                        "\n:second_place: **" + event.getGuild().retrieveMemberById(sortedList.get(1).getKey()).complete().getEffectiveName() + " - " + sortedList.get(1).getValue() + " t's**" +
                        "\n:third_place: **" + event.getGuild().retrieveMemberById(sortedList.get(2).getKey()).complete().getEffectiveName() + " - " + sortedList.get(2).getValue() + " t's**" +
                        "\n:four: " + event.getGuild().retrieveMemberById(sortedList.get(3).getKey()).complete().getEffectiveName() + " - " + sortedList.get(3).getValue() + " t's" +
                        "\n:five: " + event.getGuild().retrieveMemberById(sortedList.get(4).getKey()).complete().getEffectiveName() + " - " + sortedList.get(4).getValue() + " t's" +
                        "\n" +
                        "\nYou have said **" + new TDataJSONManager().readJsonFile(event.getAuthor().getId()) + "** t's in #t" +
                        "\n" +
                        "\nTotal t's in #t: **" + new TUtils().getTotalTs() + "**" +
                        "\n" +
                        "\n*Only the top 5 users are shown*")
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    //Amogus easter egg Command
    public void amogusEasterEggCmd(MessageReceivedEvent event) {
        event.getMessage().reply("ඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞ \n https://tenor.com/view/when-the-drip-is-sus-gif-19616035").queue();
    }

}
