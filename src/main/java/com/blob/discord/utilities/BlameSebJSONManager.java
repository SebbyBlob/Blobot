package com.blob.discord.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.Nullable;
import java.io.*;

public class BlameSebJSONManager {

    //on Startup
    public void initiateJson() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("blameseb.json")) {

            Object obj = jsonParser.parse(reader);

            JSONObject blameseb = (JSONObject) obj;
            System.out.println(blameseb);

        } catch (FileNotFoundException e) {
            createJsonFileBlameSeb();
            System.out.printf("Creating JSON file...");
        } catch (IOException | ParseException e) {
            createJsonFileBlameSeb();
            System.out.println("[Log] ParseExpection");
            e.printStackTrace();
        }
    }

    //Set Json File Values
    public Object setJsonValue(Integer integer, @Nullable Object object) {
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
            writeJsonFile(jsonObject);
            Object returnValue = blameseb + 1;
            return returnValue;
        } else if (integer == 1) {
            long forgiveseb = (long) jsonObject.get("forgiveseb");
            jsonObject.put("forgiveseb", forgiveseb + 1);
            writeJsonFile(jsonObject);
            Object returnValue = forgiveseb + 1;
            return returnValue;
        } else if (integer == 2) {
            jsonObject.put("restricted", object);
            writeJsonFile(jsonObject);
            return object;
        } else if (integer == 3) {
            jsonObject.put("toggled", object);
            writeJsonFile(jsonObject);
            return object;
        } else if (integer == 4) {
            jsonObject.put("lastSentT", object);
            writeJsonFile(jsonObject);
            return object;
        } else {
            System.out.print("ERROR: in setJsonValue()");
            return null;
        }
    }

    //Writes set values to JSON File
    public void writeJsonFile(JSONObject jsonObject) {
        try (FileWriter file = new FileWriter("blameseb.json")) {

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Reads blameseb.json
    public Object[] readJsonFile() {
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
        Long lastSentT = (Long) jsonObject.get("lastSentT");

        Object[] toReturn = new Object[]{blameseb, forgiveseb, restricted, toggled, lastSentT};
        return toReturn;
    }

    //Create blameseb.json
    private static void createJsonFileBlameSeb() {

        JSONObject blameseb = new JSONObject();
        //restricted; true = commands only allowed in bot commands, false = commands allowed in any channel
        blameseb.put("restricted", true);
        blameseb.put("toggled", true);
        blameseb.put("blameseb", 0);
        blameseb.put("forgiveseb", 0);
        blameseb.put("lastSentT", null);

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
