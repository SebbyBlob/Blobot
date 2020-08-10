package com.blob.discord.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            if (event.getChannel().getId().equals("699800487725105162")) {
                if (readJsonFile()[3].equals(true)) {
                    //
                    if (event.getMessage().getContentRaw().equalsIgnoreCase("blame seb")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[0];
                        jsonObject.put("blameseb", (long) readJsonFile()[0] + 1);
                        event.getChannel().sendMessage("**" + event.getAuthor().getName() + " has blamed Seb for the " + readJsonFile()[0] + " time!**").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("forgive seb")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[1];
                        jsonObject.put("forgiveseb", (long) readJsonFile()[1] + 1);
                        event.getChannel().sendMessage("**" + event.getAuthor().getName() + " has forgiven Seb for the " + readJsonFile()[1] + " time!**").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("is it sebs fault") ||
                            event.getMessage().getContentRaw().equalsIgnoreCase("is it seb's fault?") ||
                            event.getMessage().getContentRaw().equalsIgnoreCase("is it sebs fault?") ||
                            event.getMessage().getContentRaw().equalsIgnoreCase("iisf") ||
                            event.getMessage().getContentRaw().equalsIgnoreCase("iisf?")) {
                        long blameseb = (long) readJsonFile()[0];
                        long forgiveseb = (long) readJsonFile()[1];
                        if (blameseb > forgiveseb) {
                            event.getChannel().sendMessage("**It is Seb's fault!**").queue();
                        } else if (blameseb == forgiveseb) {
                            event.getChannel().sendMessage("**It is not Seb's fault nor his fault!**").queue();
                        } else if (blameseb < forgiveseb) {
                            event.getChannel().sendMessage("**It is not Seb's fault!**").queue();
                        }
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot cat")) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("**Cat :D**");
                        eb.setColor(new Color(64, 158, 230));
                        eb.setImage(getCat());
                        event.getChannel().sendMessage(eb.build()).queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("ping me")) {
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " ping!").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot disable") && event.getAuthor().getId().equals("400453367966466058")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[3];
                        jsonObject.put("toggled", false);
                        event.getChannel().sendMessage("Disabled Blobot commands!").queue();
                    }
                    //
                } else if (event.getAuthor().getId().equals("400453367966466058") && event.getMessage().getContentRaw().equalsIgnoreCase("blobot enable")) {
                    JSONObject jsonObject = (JSONObject) readJsonFile()[3];
                    jsonObject.put("toggled", true);
                    event.getChannel().sendMessage("Enabled Blobot commands!").queue();
                }
            } else {
                if (readJsonFile()[3].equals(true) && readJsonFile()[2].equals(false)) {
                    //
                    if (event.getMessage().getContentRaw().equalsIgnoreCase("blame seb")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[0];
                        jsonObject.put("blameseb", (long) readJsonFile()[0] + 1);
                        event.getChannel().sendMessage("**" + event.getAuthor().getName() + " has blamed Seb for the " + readJsonFile()[0] + " time!**").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("forgive seb")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[1];
                        jsonObject.put("forgiveseb", (long) readJsonFile()[1] + 1);
                        event.getChannel().sendMessage("**" + event.getAuthor().getName() + " has forgiven Seb for the " + readJsonFile()[1] + " time!**").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("is it sebs fault") ||
                            event.getMessage().getContentRaw().equalsIgnoreCase("is it seb's fault?") ||
                            event.getMessage().getContentRaw().equalsIgnoreCase("is it sebs fault?") ||
                            event.getMessage().getContentRaw().equalsIgnoreCase("iisf") ||
                            event.getMessage().getContentRaw().equalsIgnoreCase("iisf?")) {
                        long blameseb = (long) readJsonFile()[0];
                        long forgiveseb = (long) readJsonFile()[1];
                        if (blameseb > forgiveseb) {
                            event.getChannel().sendMessage("**It is Seb's fault!**").queue();
                        } else if (blameseb == forgiveseb) {
                            event.getChannel().sendMessage("**It is not Seb's fault nor his fault!**").queue();
                        } else if (blameseb < forgiveseb) {
                            event.getChannel().sendMessage("**It is not Seb's fault!**").queue();
                        }
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot cat")) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("**Cat :D**");
                        eb.setColor(new Color(64, 158, 230));
                        eb.setImage(getCat());
                        event.getChannel().sendMessage(eb.build()).queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("ping me")) {
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " ping!").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot disable") && event.getAuthor().getId().equals("400453367966466058")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[3];
                        jsonObject.put("toggled", false);
                        event.getChannel().sendMessage("Disabled Blobot commands!").queue();
                    }
                    //
                } else if (readJsonFile()[3].equals(false)) {
                    if (event.getAuthor().getId().equals("400453367966466058") && event.getMessage().getContentRaw().equalsIgnoreCase("blobot enable")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[3];
                        jsonObject.put("toggled", true);
                        event.getChannel().sendMessage("Enabled Blobot commands!");
                    }
                }
            }
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " This is an automated Bot and therefore this message will not be seen, Sorry!").queue();
        }

    }

    private Object[] readJsonFile() {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("blameseb.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;

        long blameseb = (long) jsonObject.get("blameseb");
        long forgiveseb = (long) jsonObject.get("forgiveseb");
        boolean restricted = (boolean) jsonObject.get("restricted");
        boolean toggled = (boolean) jsonObject.get("toggled");

        Object[] toReturn = new Object[]{blameseb, forgiveseb, restricted, toggled};
        return toReturn;
    }

    private String getCat() {
        try {
            URL url = new URL("https://api.thecatapi.com/v1/images/search");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            Object parser = new JSONParser().parse(reader);
            JSONArray jsonArray = (JSONArray) parser;

            String imageUrl = (String) jsonArray.get(0);

            return imageUrl;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
