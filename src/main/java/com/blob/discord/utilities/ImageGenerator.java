package com.blob.discord.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ImageGenerator {

    private Random random = new Random();
    private HashMap<String, String> exclude = new HashMap<>();

    //Gets a random hedgehog image src URL from the searched hedgehog image list
    public String getHedgehog() {
        List<String> imagesList = new HtmlReader().getHedgehogImagesList();
        imagesList.remove(exclude.get("hedgehog"));
        String imageSrc = imagesList.get(random.nextInt(imagesList.size()));
        exclude.put("hedgehog", imageSrc);
        return imageSrc;
    }

    //Gets a random groundhog image src URL from the searched groundhog image list
    public String getGroundhog() {
        List<String> imagesList = new HtmlReader().getGroundhogImagesList();
        imagesList.remove(exclude.get("groundhog"));
        String imageSrc = imagesList.get(random.nextInt(imagesList.size()));
        exclude.put("groundhog", imageSrc);
        return imageSrc;
    }

    //Gets a random dog image src URL from the searched dog image list
    public String getDog() {
        List<String> imagesList = new HtmlReader().getDogImagesList();
        imagesList.remove(exclude.get("dog"));
        String imageSrc = imagesList.get(random.nextInt(imagesList.size()));
        exclude.put("dog", imageSrc);
        return imageSrc;
    }

}
