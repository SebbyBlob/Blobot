package com.blob.discord.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.Nullable;
import java.io.BufferedReader;
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
                        setJsonValue(0, null);
                        event.getChannel().sendMessage("**" + event.getAuthor().getName() + " has blamed Seb for the " + readJsonFile()[0] + " time!**").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("forgive seb")) {
                        setJsonValue(1, null);
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
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("cat")) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("**Cat :D**");
                        eb.setColor(new java.awt.Color(64, 158, 230));
                        eb.setImage(getCat());
                        event.getChannel().sendMessage(eb.build()).queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("ping me")) {
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " ping!").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot disable") && event.getAuthor().getId().equals("400453367966466058")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[3];
                        jsonObject.put("toggled", false);
                        event.getChannel().sendMessage("Disabled Blobot commands!").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("blobot restricted")) {
                        boolean restrictedState = (boolean) readJsonFile()[2];
                        if (restrictedState == true) {
                            setJsonValue(2, false);
                            event.getChannel().sendMessage("Blobot commands are now allowed in all channels.").queue();
                        } else  {
                            setJsonValue(2, true);
                            event.getChannel().sendMessage("Blobot commands are now Only allowed in " + event.getJDA().getGuildChannelById("") + " .").queue();
                        }
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
                        setJsonValue(0, null);
                        event.getChannel().sendMessage("**" + event.getAuthor().getName() + " has blamed Seb for the " + readJsonFile()[0] + " time!**").queue();
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("forgive seb")) {
                        setJsonValue(1, null);
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
                    } else if (event.getMessage().getContentRaw().equalsIgnoreCase("cat")) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("**Cat :D**");
                        eb.setColor(new java.awt.Color(64, 158, 230));
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

    private Object setJsonValue(Integer integer, @Nullable Object object) {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("blameseb.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;


        if (integer == 0) {
            long blameseb = (long) jsonObject.get("blameseb");
            jsonObject.put("blameseb", blameseb + 1);
            Object returnValue = blameseb + 1;
            return returnValue;
        } else if (integer == 1) {
            long forgiveseb = (long) jsonObject.get("forgiveseb");
            jsonObject.put("forgiveseb", forgiveseb + 1);
            Object returnValue = forgiveseb + 1;
            return returnValue;
        } else if (integer == 2) {
            jsonObject.put("restricted", object);
            return object;
        } else if (integer == 3) {
            jsonObject.put("toggled", object);
            return object;
        } else {
            System.out.printf("ERROR: in setJsonValue()");
            return null;
        }
    }

    private String getCat() {
        try {
            URL url = new URL("https://api.thecatapi.com/v1/images/search");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            Object parser = new JSONParser().parse(reader);
            JSONArray jsonArray = (JSONArray) parser;

            JSONObject imageUrlJson = (JSONObject) jsonArray.get(0);
            String imageUrl = (String) imageUrlJson.get("url");

            return imageUrl;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
