package com.blob.discord.commands;

import com.blob.discord.listeners.MessageListener;
import com.blob.discord.managers.Command;
import com.blob.discord.utilities.ImageGenerator;
import com.blob.discord.utilities.RoleUtils;
import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FunCmds extends Command {

    public FunCmds() {
        super("");
    }

    @Override
    public void onCommand(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().matches("quickmaths|quick maths")) {
            quickMaths(event);
        } else if (event.getMessage().getContentRaw().toLowerCase().matches("quickmaths end|quick maths end|quickmaths stop|quick maths stop")) {
            endQuickMaths(event);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("cat")) {
            cat(event);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("dog")) {
            dog(event);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("hedgehog")) {
            hedgehog(event);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("groundhog")) {
            groundhog(event);
        } else if (event.getMessage().getContentRaw().equalsIgnoreCase("ping me")) {
            pingMe(event);
        }
    }

    private static boolean quickMathsInProgress = false;
    private static String answer = "";
    private static String equation = "";
    private static MessageChannel channelStarted;
    private static long creationTime;

    //Starts the quick maths mini event
    public void quickMaths(MessageReceivedEvent event) {
        if (!new RoleUtils().hasRole(event.getMember(), "Owner", event.getGuild())) {
            event.getMessage().reply(Settings.NoPermissionMessage).queue(message1 -> {
                event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });
            return;
        }
        if (quickMathsInProgress == true) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " You cannot start Quick Maths while it is already in progress!").queue(message1 -> {
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });;
        } else {
            event.getMessage().delete().queue();

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
                int expression1 = random.nextInt(500);
                int expression2 = random.nextInt(expression1 - 10);
                equation = expression1 + "-" + expression2;
                answerInt = expression1 - expression2;
            } else {
                //Multiplication
                int expression1 = random.nextInt(13);
                int expression2 = random.nextInt(expression1 - 5);
                equation = expression1 + "x" + expression2;
                answerInt = expression1 * expression2;
            }
            answer = String.valueOf(answerInt);
            quickMathsInProgress = true;
            channelStarted = event.getChannel();
            creationTime = System.currentTimeMillis();

            event.getChannel().sendMessage("**QUICK MATHS:** First 3 people to answer are cool!" +
                    "\n**QUICK MATHS:** Solve: **" + equation + "**").queue();
        }
    }

    //Stops quickmaths if it is currently in progress
    public void endQuickMaths(MessageReceivedEvent event) {
        if (!new RoleUtils().hasRole(event.getMember(), "Owner", event.getGuild())) {
            event.getMessage().reply(Settings.NoPermissionMessage).queue(message1 -> {
                event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });
            return;
        }
        event.getMessage().delete().queue();
        if (quickMathsInProgress == true) {
            if (event.getChannel().equals(channelStarted)) {
                quickMathsInProgress = false;
                new MessageListener().setUsersAnswered(1);
                new MessageListener().getUsersCompleted().clear();
                event.getChannel().sendMessage("**QUICK MATHS OVER:** " + event.getMember().getEffectiveName() + " has force ended this Quick Maths game").queue();
            } else {
                event.getChannel().sendMessage(event.getMember().getAsMention() + " There is currently no ongoing Quick Maths game in this channel!").queue(message1 -> {
                    message1.delete().queueAfter(3, TimeUnit.SECONDS);
                });
            }
        } else {
            event.getChannel().sendMessage(event.getMember().getAsMention() + " There is currently no ongoing Quick Maths game!").queue(message1 -> {
                message1.delete().queueAfter(3, TimeUnit.SECONDS);
            });
        }
    }

    //Gives you a random Cat image
    public void cat(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Cat :D**")
                .setImage(new ImageGenerator().getCat())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    //Gives you a random Dog image
    public void dog(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Dog :dog:**")
                .setImage(new ImageGenerator().getDog())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    //Gives a random hedgehog image
    public void hedgehog(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Hedgehog :hedgehog:**")
                .setImage(new ImageGenerator().getHedgehog())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    //Gives a random groundhog image
    public void groundhog(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(251, 118, 31))
                .setTitle("**Groundhog**")
                .setImage(new ImageGenerator().getGroundhog())
                .setFooter("Developed by Sebby", "https://i.imgur.com/PpzENVl.png");
        event.getChannel().sendMessage(eb.build()).queue();
    }

    //Ping yourself command
    public void pingMe(MessageReceivedEvent event) {
        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " ping pong!").queue();
    }

    public static boolean isQuickMathsInProgress() { return quickMathsInProgress; }
    public static void setQuickMathsInProgress(boolean quickMathsInProgress) { FunCmds.quickMathsInProgress = quickMathsInProgress; }
    public static String getAnswer() { return answer; }
    public static long getCreationTime() { return creationTime; }
    public static String getEquation() { return equation; }
    public static MessageChannel getChannelStarted() { return channelStarted; }

}
