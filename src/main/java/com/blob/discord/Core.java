package com.blob.discord;

import com.blob.discord.listeners.MessageListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Core {

    public static void main(String[] args) throws LoginException {

        JDABuilder builder = JDABuilder.createDefault("NzQxNzgwNzA3MTA5NzY1MTUw.Xy8jHg.lSPAcTKoBjgwVFR78_BDEGojPKk");

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.addEventListeners(new MessageListener());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("TGU"));

        builder.build();

        //

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("blameseb.json")) {

            Object obj = jsonParser.parse(reader);

            JSONObject blameseb = (JSONObject) obj;
            System.out.println(blameseb);

        } catch (FileNotFoundException e) {
            createJsonFile();
            System.out.printf("Creating JSON file...");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public static void createJsonFile() {

        JSONObject blameseb = new JSONObject();
        //restricted; true = commands only allowed in bot commands, false = commands allowed in any channel
        blameseb.put("restricted", true);
        blameseb.put("toggled", true);
        blameseb.put("blameseb", 0);
        blameseb.put("forgiveseb", 0);

        /*try {
            Files.write(Paths.get("blameseb.json"), blameseb.toJSONString().getBytes());
            System.out.printf("Created JSON file.");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try (FileWriter file = new FileWriter("blameseb.json")) {

            file.write(blameseb.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
