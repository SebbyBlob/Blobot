package com.blob.discord.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CatGenerator {

    //Grabs a random cat image src URL from Cat API
    public String getCat() {
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
