package com.blob.discord.commands;

import com.blob.discord.managers.Command;
import com.blob.discord.managers.TDataJSONManager;
import com.blob.discord.utilities.TUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OtherCmds extends Command {

    Random Random = new Random();

    public OtherCmds() {
        super("tleaderboard", "t leaderboard", "amogus", "sus");
    }

    @Override
    public void onCommand(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().matches("t leaderboard|tleaderboard")) {
            tLeaderboard(event);
        } else if (event.getMessage().getContentRaw().toLowerCase().matches("amogus|sus")) {
            amogusEasterEggCmd(event);
        }
    }

    //T Leaderboard Command
    public void tLeaderboard(MessageReceivedEvent event) {
        if (TUtils.getInstance().isTLoaded == false) {
            event.getMessage().reply("**Blobot is currently calculating & crunching the t's**" +
                    "\nTry running this command again in a few seconds...").queue();
            return;
        }

        List<Map.Entry<String, Long>> sortedList = new TUtils().tSorter();
        StringBuilder stringBuilder = new StringBuilder();

        //TODO: Add player leave protection
        //Retrieve top 5 t members
        event.getGuild().retrieveMembersByIds(sortedList.get(0).getKey(), sortedList.get(1).getKey()
                , sortedList.get(2).getKey(), sortedList.get(3).getKey(), sortedList.get(4).getKey()).onSuccess(members -> {
            //Appends top 5 t users to the Stringbuilder
            stringBuilder.append("\n:first_place: **" + members.get(0).getEffectiveName() + " - " + sortedList.get(0).getValue() + " t's**");
            stringBuilder.append("\n:second_place: **" + members.get(1).getEffectiveName() + " - " + sortedList.get(1).getValue() + " t's**");
            stringBuilder.append("\n:third_place: **" + members.get(2).getEffectiveName() + " - " + sortedList.get(2).getValue() + " t's**");
            stringBuilder.append("\n:four: " + members.get(3).getEffectiveName() + " - " + sortedList.get(3).getValue() + " t's");
            stringBuilder.append("\n:five: " + members.get(4).getEffectiveName() + " - " + sortedList.get(4).getValue() + " t's");

            //Appends additional information
            stringBuilder.append("\n" +
                    "\nYou have said **" + new TDataJSONManager().readJsonFile(event.getAuthor().getId()) + "** t's in #t" +
                    "\n" +
                    "\nTotal t's in #t: **" + new TUtils().getTotalTs() + "**" +
                    "\n" +
                    "\n*Only the top 5 users are shown*");
            //Creates new message Embed
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(new Color(248, 139, 78))
                    .setTitle("**:regional_indicator_t:   #t Leaderboard   :regional_indicator_t:**")
                    .setDescription(stringBuilder.toString())
                    .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
            //Sends completed message embed
            event.getChannel().sendMessage(eb.build()).queue();
        });
    }

    //Amogus easter egg Command
    public void amogusEasterEggCmd(MessageReceivedEvent event) {
        if (Random.nextInt(3) == 1)
            event.getMessage().reply("ඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞ \n https://tenor.com/view/when-the-drip-is-sus-gif-19616035").queue();
    }

}
