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

        StringBuilder stringBuilder = new StringBuilder();

        event.getGuild().retrieveMembersByIds(sortedList.get(0).getKey(), sortedList.get(1).getKey()
                , sortedList.get(2).getKey(), sortedList.get(3).getKey(), sortedList.get(4).getKey()).onSuccess(members -> {

            stringBuilder.append("\n:first_place: **" + members.get(0).getEffectiveName() + " - " + sortedList.get(0).getValue() + " t's**");
            stringBuilder.append("\n:second_place: **" + members.get(1).getEffectiveName() + " - " + sortedList.get(1).getValue() + " t's**");
            stringBuilder.append("\n:third_place: **" + members.get(2).getEffectiveName() + " - " + sortedList.get(2).getValue() + " t's**");
            stringBuilder.append("\n:four: " + members.get(3).getEffectiveName() + " - " + sortedList.get(3).getValue() + " t's");
            stringBuilder.append("\n:five: " + members.get(4).getEffectiveName() + " - " + sortedList.get(4).getValue() + " t's");

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


                    /*event.getGuild().retrieveMemberById(
                            sortedList.get(0).getKey())
                    .flatMap(member -> {
                        stringBuilder.append("\n");
                        stringBuilder.append("\n:first_place: **" + member.getEffectiveName() + " - " + sortedList.get(0).getValue() + " t's**");
                        return event.getGuild().retrieveMemberById(sortedList.get(1).getKey());
                    }).flatMap(member -> {
                        stringBuilder.append("\n:second_place: **").append(member.getEffectiveName()).append(" - ").append(sortedList.get(1).getValue()).append(" t's**");
                        return event.getGuild().retrieveMemberById(sortedList.get(2).getKey());
                    }).flatMap(member -> {
                        stringBuilder.append("\n:third_place: **").append(member.getEffectiveName()).append(" - ").append(sortedList.get(2).getValue()).append(" t's**");
                        return event.getGuild().retrieveMemberById(sortedList.get(3).getKey());
                    }).flatMap(member -> {
                        stringBuilder.append("\n:four: ").append(member.getEffectiveName()).append(" - ").append(sortedList.get(3).getValue()).append(" t's");
                        return event.getGuild().retrieveMemberById(sortedList.get(4).getKey());
                    }).flatMap(member -> {
                        stringBuilder.append("\n:five: ").append(member.getEffectiveName()).append(" - ").append(sortedList.get(4).getValue()).append(" t's");
                        return event.getGuild().retrieveMemberById(sortedList.get(4).getKey());
            }).queue(member -> {
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
            });*/
        });

        /*event.getGuild().retrieveMemberById(sortedList.get(0).getKey())
                .flatMap(member -> {
                    stringBuilder.append("\n");
                    stringBuilder.append("\n:first_place: **" + member.getEffectiveName() + " - " + sortedList.get(0).getValue() + " t's**");
                    return event.getGuild().retrieveMemberById(sortedList.get(1).getKey());
                }).flatMap(member -> {
                    stringBuilder.append("\n:second_place: **").append(member.getEffectiveName()).append(" - ").append(sortedList.get(1).getValue()).append(" t's**");
                    return event.getGuild().retrieveMemberById(sortedList.get(2).getKey());
                }).flatMap(member -> {
                    stringBuilder.append("\n:third_place: **").append(member.getEffectiveName()).append(" - ").append(sortedList.get(2).getValue()).append(" t's**");
                    return event.getGuild().retrieveMemberById(sortedList.get(3).getKey());
                }).flatMap(member -> {
                    stringBuilder.append("\n:four: ").append(member.getEffectiveName()).append(" - ").append(sortedList.get(3).getValue()).append(" t's");
                    return event.getGuild().retrieveMemberById(sortedList.get(4).getKey());
                }).flatMap(member -> {
                    stringBuilder.append("\n:five: ").append(member.getEffectiveName()).append(" - ").append(sortedList.get(4).getValue()).append(" t's");
                    return event.getGuild().retrieveMemberById(sortedList.get(4).getKey());
                }).queue(member -> {
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
        });*/


        /*event.getGuild().retrieveMemberById(sortedList.get(0).getKey()).queue(member -> {
            stringBuilder.append("\n:first_place: **").append(member.getEffectiveName()).append(" - ").append(sortedList.get(0).getValue()).append(" t's**");

            event.getGuild().retrieveMemberById(sortedList.get(1).getKey()).queue(member1 -> {
                System.out.println("Test");
                stringBuilder.append("\n:second_place: **").append(member1.getEffectiveName()).append(" - ").append(sortedList.get(1).getValue()).append(" t's**");

                event.getGuild().retrieveMemberById(sortedList.get(2).getKey()).queue(member2 -> {
                    stringBuilder.append("\n:third_place: **").append(member2.getEffectiveName()).append(" - ").append(sortedList.get(2).getValue()).append(" t's**");

                    event.getGuild().retrieveMemberById(sortedList.get(3).getKey()).queue(member3 -> {
                        stringBuilder.append("\n:four: ").append(member3.getEffectiveName()).append(" - ").append(sortedList.get(3).getValue()).append(" t's");

                        event.getGuild().retrieveMemberById(sortedList.get(4).getKey()).queue(member4 -> {
                            stringBuilder.append("\n:five: ").append(member4.getEffectiveName()).append(" - ").append(sortedList.get(4).getValue()).append(" t's");
                        });
                    });
                });
            });
        });*/

        /*stringBuilder.append("\n" +
                "\nYou have said **" + new TDataJSONManager().readJsonFile(event.getAuthor().getId()) + "** t's in #t" +
                "\n" +
                "\nTotal t's in #t: **" + new TUtils().getTotalTs() + "**" +
                "\n" +
                "\n*Only the top 5 users are shown*");


        System.out.println(stringBuilder.toString());

        //Creates new message Embed
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(248, 139, 78))
                .setTitle("**:regional_indicator_t:   #t Leaderboard   :regional_indicator_t:**")
                .setDescription(stringBuilder.toString())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();*/
        /*
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
         */
    }

    //Amogus easter egg Command
    public void amogusEasterEggCmd(MessageReceivedEvent event) {
        if (Random.nextInt(3) == 1)
            event.getMessage().reply("ඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞඞ \n https://tenor.com/view/when-the-drip-is-sus-gif-19616035").queue();
    }

}
