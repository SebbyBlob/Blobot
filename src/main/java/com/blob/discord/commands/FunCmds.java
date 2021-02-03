package com.blob.discord.commands;

import com.blob.discord.listeners.MessageListener;
import com.blob.discord.utilities.CatGenerator;
import com.blob.discord.utilities.ImageGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FunCmds {

    private static boolean quickMathsInProgress = false;
    private static String answer = "";
    private static String equation = "";
    private static MessageChannel channelStarted;
    private static long creationTime;

    //Starts the quick maths mini event
    public void quickMaths(MessageChannel channel, Message message, User user) {
        if (quickMathsInProgress == true) {
            message.delete().queue();
            channel.sendMessage(user.getAsMention() + " You cannot start Quick Maths while it is already in progress!").queue(message1 -> {
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });;
        } else {
            message.delete().queue();

            Random random = new Random();

            int answerInt = 0;

            int randomType = random.nextInt(3);
            if (randomType == 0) {
                //Addition
                int expression1 = random.nextInt(1000);
                int expression2 = random.nextInt(1000);
                equation = expression1 + "+" + expression2;
                answerInt = expression1 + expression2;
            } else if (randomType == 1) {
                //Subtraction
                int expression1 = random.nextInt(1000);
                int expression2 = random.nextInt(expression1 - 10);
                equation = expression1 + "-" + expression2;
                answerInt = expression1 - expression2;
            } else {
                //Multiplication
                int expression1 = random.nextInt(50);
                int expression2 = random.nextInt(50);
                equation = expression1 + "x" + expression2;
                answerInt = expression1 * expression2;
            }
            answer = String.valueOf(answerInt);
            System.out.println(answer);
            quickMathsInProgress = true;
            channelStarted = channel;
            creationTime = System.currentTimeMillis();

            channel.sendMessage("**QUICK MATHS:** First 3 people to answer are cool!" +
                    "\n**QUICK MATHS:** Solve: **" + equation + "**").queue();
        }
    }

    //Stops quickmaths if it is currently in progress
    public void endQuickMaths(Message message, MessageChannel channel, User user) {
        message.delete().queue();
        if (quickMathsInProgress == true) {
            if (channel.equals(channelStarted)) {
                quickMathsInProgress = false;
                new MessageListener().setUsersAnswered(1);
                new MessageListener().getUsersCompleted().clear();
                channel.sendMessage("**QUICK MATHS OVER:** " + user.getAsMention() + " has force ended this Quick Maths game").queue();
            } else {
                channel.sendMessage(user.getAsMention() + " There is currently no ongoing Quick Maths game in this channel!").queue(message1 -> {
                    message1.delete().queueAfter(3, TimeUnit.SECONDS);
                });
            }
        } else {
            channel.sendMessage(user.getAsMention() + " There is currently no ongoing Quick Maths game!").queue(message1 -> {
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });
        }
    }

    //Gives you a random Cat image

    public void cat(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Cat :D**")
                .setImage(new CatGenerator().getCat())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Gives you a random Dog image

    public void dog(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Dog :dog:**")
                .setImage(new ImageGenerator().getDog())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Gives a random hedgehog image

    public void hedgehog(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Hedgehog :hedgehog:**")
                .setImage(new ImageGenerator().getHedgehog())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Gives a random groundhog image

    public void groundhog(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Groundhog**")
                .setImage(new ImageGenerator().getGroundhog())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        channel.sendMessage(eb.build()).queue();
    }

    //Ping yourself command

    public void pingMe(MessageChannel channel, User author) {
        channel.sendMessage(author.getAsMention() + " ping pong!").queue();
    }

    public static boolean isQuickMathsInProgress() { return quickMathsInProgress; }
    public static void setQuickMathsInProgress(boolean quickMathsInProgress) { FunCmds.quickMathsInProgress = quickMathsInProgress; }
    public static String getAnswer() { return answer; }
    public static long getCreationTime() { return creationTime; }
    public static String getEquation() { return equation; }
    public static MessageChannel getChannelStarted() { return channelStarted; }

}
