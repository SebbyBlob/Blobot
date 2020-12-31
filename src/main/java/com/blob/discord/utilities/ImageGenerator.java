package com.blob.discord.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ImageGenerator {

    private Random random = new Random();
    private HashMap<String, String> exclude = new HashMap<>();

    public String getHedgehog() {
        List<String> imagesList = new HtmlReader().getHedgehogImagesList();
        imagesList.remove(exclude.get("hedgehog"));
        String imageSrc = imagesList.get(random.nextInt(imagesList.size()));
        exclude.put("hedgehog", imageSrc);
        return imageSrc;
    }

    public String getGroundhog() {
        List<String> imagesList = new HtmlReader().getGroundhogImagesList();
        imagesList.remove(exclude.get("groundhog"));
        String imageSrc = imagesList.get(random.nextInt(imagesList.size()));
        exclude.put("groundhog", imageSrc);
        return imageSrc;
    }

}
