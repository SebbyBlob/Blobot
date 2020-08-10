package com.blob.discord.listeners;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.isFromType(ChannelType.TEXT)) {
            if (event.getChannel().getId().equals("699800487725105162")) {
                if (readJsonFile()[3].equals(true)) {
                    //
                    if (event.getMessage().getContentRaw().equals("blame seb")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[0];
                        jsonObject.put("blameseb", (int) readJsonFile()[0] + 1);
                        event.getChannel().sendMessage("**"+event.getAuthor().getName()+" has blamed Seb for the "+readJsonFile()[0]+" time!**").queue();
                    } else if (event.getMessage().getContentRaw().equals("forgive seb")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[1];
                        jsonObject.put("forgiveseb", (int) readJsonFile()[1] + 1);
                        event.getChannel().sendMessage("**"+event.getAuthor().getName()+" has forgiven Seb for the "+readJsonFile()[1]+" time!**").queue();
                    } else if (event.getMessage().getContentRaw().equals("is it sebs fault") ||
                               event.getMessage().getContentRaw().equals("is it seb's fault?") ||
                               event.getMessage().getContentRaw().equals("is it sebs fault?") ||
                               event.getMessage().getContentRaw().equals("iisf") ||
                               event.getMessage().getContentRaw().equals("iisf?")) {
                        int blameseb = (int) readJsonFile()[0];
                        int forgiveseb = (int) readJsonFile()[1];
                        if (blameseb > forgiveseb) {
                            event.getChannel().sendMessage("**It is Seb's fault!**").queue();
                        } else if (blameseb == forgiveseb) {
                            event.getChannel().sendMessage("**It is not Seb's fault nor his fault!**").queue();
                        } else if (blameseb < forgiveseb) {
                            event.getChannel().sendMessage("**It is not Seb's fault!**").queue();
                        }
                    } else if (event.getMessage().getContentRaw().equals("ping me")) {
                        event.getChannel().sendMessage(event.getAuthor().getAsMention()+" ping!").queue();
                    } else if (event.getMessage().getContentRaw().equals("blobot disable") && event.getAuthor().getId().equals("400453367966466058")) {
                        JSONObject jsonObject = (JSONObject)
                        event.getChannel().sendMessage("Disabled Blobot commands!").queue();
                    }
                        //
                } else if (event.getAuthor().getId().equals("400453367966466058") && event.getMessage().getContentRaw().equals("blobot enable")) {
                    JSONObject jsonObject = (JSONObject) readJsonFile()[3];
                    jsonObject.put("toggled", true);
                }
            } else {
                if (readJsonFile()[3].equals(true) && readJsonFile()[2].equals(false)) {

                } else if (readJsonFile()[3].equals(false)) {
                    if (event.getAuthor().getId().equals("400453367966466058") && event.getMessage().getContentRaw().equals("blobot enable")) {
                        JSONObject jsonObject = (JSONObject) readJsonFile()[3];
                        jsonObject.put("toggled", true);
                    }
                }
            }
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " This is an automated Bot and therefore this message will not be seen, Sorry!").queue();
        }

    }

    public Object[] readJsonFile() {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("blameseb.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;

        int blameseb = (int) jsonObject.get("blameseb");
        int forgiveseb = (int) jsonObject.get("forgiveseb");
        boolean restricted = (boolean) jsonObject.get("restricted");
        boolean toggled = (boolean) jsonObject.get("toggled");

        Object[] toReturn = new Object[]{blameseb, forgiveseb, restricted, toggled};
        return toReturn;
    }

}
