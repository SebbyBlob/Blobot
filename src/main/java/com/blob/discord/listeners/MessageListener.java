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

            } else {
                JSONObject jsonObject;
                try {
                    jsonObject = (JSONObject) readJsonFile();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }

                if (jsonObject.get("restricted") == false) {

                }
            }

            if (event.getMessage().getContentDisplay().equals("")) {

            } else if (event.getMessage().getContentDisplay().equals("")) {

            }

            //

            else if (event.getChannel().getId().equals("699800487725105162")) {
                if (event.getMessage().getContentDisplay().equals("blame seb")) {

                } else if (event.getMessage().getContentDisplay().equals("forgive seb")) {

                } else if (event.getMessage().getContentDisplay().equals("")) {

                } else if (event.getMessage().getContentDisplay().equals("ping me")) {
                    event.getChannel().sendMessage(event.getAuthor().getAsMention() + " ping!").queue();
                }
            }
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " This is an automated Bot and therefore this message will not be seen, Sorry!").queue();
        }

    }

    public Object readJsonFile() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("blameseb.json"));
        JSONArray jsonArray = (JSONArray) obj;
        //JSONObject jsonObject = (JSONObject) jsonArray.get("");

        int blameseb = (int) jsonObject.get("blameseb");
        int forgiveseb = (int) jsonObject.get("forgiveseb");
        boolean restricted = (boolean) jsonObject.get("restricted");

        Object[] toReturn = {blameseb, forgiveseb, restricted};
        return toReturn;
    }

}
