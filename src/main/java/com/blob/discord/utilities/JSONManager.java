package com.blob.discord.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONManager {

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

        Object[] toReturn = new Object[]{blameseb, forgiveseb, restricted, toggled};
        return toReturn;
    }

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
        } else {
            System.out.printf("ERROR: in setJsonValue()");
            return null;
        }
    }

    public void writeJsonFile(JSONObject jsonObject) {
        try (FileWriter file = new FileWriter("blameseb.json")) {

            file.write(jsonObject.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //

    public void initiateJson() {
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

    private static void createJsonFile() {

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
