package com.blob.discord.managers;

import com.blob.discord.Core;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class EasterEggJSONManager {

    //on Startup
    public void initiateJson() {
        JSONParser jsonParser = new JSONParser();

        //Tries to read the file tdata.json
        try (FileReader reader = new FileReader("easteregg.json")) {

            Object obj = jsonParser.parse(reader);

            JSONObject easteregg = (JSONObject) obj;
            Core.getLogger().info("easteregg.json values: " + easteregg);

        } catch (FileNotFoundException e) {
            //Creates file if not found
            createJsonFileTData();
            System.out.printf("Creating JSON file...");
        } catch (IOException | ParseException e) {
            createJsonFileTData();
            System.out.println("[Log] ParseExpection");
            e.printStackTrace();
        }
    }

    //Sets tdata value to tdata.json
    public void addEastereggJsonValue(String userId, Integer easterEggType) {
        Object obj = null;
        //Tries to read easteregg.json file
        try {
            obj = new JSONParser().parse(new FileReader("easteregg.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return;
        }
        JSONArray jsonArray = (JSONArray) obj;

        JSONObject userJsonObject = null;

        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            jsonObject.key
            if (jsonObject.n) {
                userJsonObject = jsonObject
            }
        }

        if (jsonArray.contains(userId)) {
            JSONObject jsonObject = (JSONObject) jsonArray.get("");
        } else {

        }


        //Adds or sets to the "t" value of certain user
        long userTs = readJsonFile(userId);
        if (!(userTs == 0)) {
            jsonObject.put(userId, userTs + 1);
        } else {
            jsonObject.put(userId, 1);
        }
        writeJsonFile(jsonObject);
    }

    //Reads tdata.json
    public Long readJsonFile(String userId) {
        Object obj = null;
        //Tries to read tdata.json file
        try {
            obj = new JSONParser().parse(new FileReader("easteregg.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;

        //Gets the "t" value of a certain user
        if (jsonObject.containsKey(userId)) {
            return (Long) jsonObject.get(userId);
        } else {
            return 0L;
        }

    }

    //Writes set values to JSON File
    public void writeJsonFile(JSONObject jsonObject) {
        //Tries to read tdata.json file
        try (FileWriter file = new FileWriter("easteregg.json")) {

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Gets tdata.json as a JSONObject
    public JSONObject getTDataJsonObject() {
        Object obj = null;
        //Tries to read tdata.json file
        try {
            obj = new JSONParser().parse(new FileReader("tdata.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject;
    }

    //Creates the empty format inside tdata.json
    private static void createJsonFileTData() {
        //Tries to read tdata.json file
        try (FileWriter file = new FileWriter("tdata.json")) {

            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("{");
            printWriter.print("}");
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
