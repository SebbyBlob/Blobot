package com.blob.discord.utilities;

import com.blob.discord.Core;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HtmlReader {

    private WebDriver driver;
    private static List<String> hedgehogImagesList = new ArrayList<String>();
    private static List<String> groundhogImagesList = new ArrayList<String>();
    private static List<String> dogImagesList = new ArrayList<String>();

    //Runs on bot Startup
    public void onStartup() {
        findHedgehogImages();
        findGroundhogImages();
        findDogImages();
        Core.getLogger().info("Successfully grabbed ALL images!");
    }

    //Searches google for 200 hedgehog images
    private void findHedgehogImages() {
        try {
            //System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
            //System.setProperty("webdriver.chrome.driver", "C:\\usr\\bin\\chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            driver = new ChromeDriver(chromeOptions);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            driver.get("https://www.google.com/search?tbm=isch&tbs=itp:photo&q=hedgehog");

            for (int amount = 0; amount < 200;) {
                js.executeScript("scroll(0, 100000)");
                TimeUnit.SECONDS.sleep(1);

                String html = driver.getPageSource();
                Document doc = Jsoup.parse(html);
                Elements imgs = doc.select("[data-src]");

                for (Element el : imgs) {
                    if (amount < 200) {
                        if (Integer.parseInt(imgs.attr("height")) > 50) {
                            String sourceUrl = el.attr("abs:data-src");
                            amount++;
                            hedgehogImagesList.add(sourceUrl);
                        }
                    }
                }
            }
            Core.getLogger().info("Successfully grabbed 200 Hedgehog images");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Searches google for 200 groundhog images
    private void findGroundhogImages() {
        try {
            //System.setProperty("webdriver.chrome.driver", "C:\\usr\\bin\\chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            driver = new ChromeDriver(chromeOptions);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            driver.get("https://www.google.com/search?tbm=isch&tbs=itp:photo&q=groundhog");

            for (int amount = 0; amount < 200;) {
                js.executeScript("scroll(0, 100000)");
                TimeUnit.SECONDS.sleep(1);

                String html = driver.getPageSource();
                Document doc = Jsoup.parse(html);
                Elements imgs = doc.select("[data-src]");

                for (Element el : imgs) {
                    if (amount < 200) {
                        if (Integer.parseInt(imgs.attr("height")) > 50) {
                            String sourceUrl = el.attr("abs:data-src");
                            amount++;
                            groundhogImagesList.add(sourceUrl);
                        }
                    }
                }
            }
            Core.getLogger().info("Successfully grabbed 200 Groundhog images");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Searches google for 200 dog images
    private void findDogImages() {
        try {
            //System.setProperty("webdriver.chrome.driver", "C:\\usr\\bin\\chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            driver = new ChromeDriver(chromeOptions);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            driver.get("https://www.google.com/search?tbm=isch&tbs=itp:photo&q=dog");

            for (int amount = 0; amount < 200;) {
                js.executeScript("scroll(0, 100000)");
                TimeUnit.SECONDS.sleep(1);

                String html = driver.getPageSource();
                Document doc = Jsoup.parse(html);
                Elements imgs = doc.select("[data-src]");

                for (Element el : imgs) {
                    if (amount < 200) {
                        if (Integer.parseInt(imgs.attr("height")) > 50) {
                            String sourceUrl = el.attr("abs:data-src");
                            amount++;
                            dogImagesList.add(sourceUrl);
                        }
                    }
                }
            }
            Core.getLogger().info("Successfully grabbed 200 Dog images");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getHedgehogImagesList() { return hedgehogImagesList; }
    public List<String> getGroundhogImagesList() { return groundhogImagesList; }
    public List<String> getDogImagesList() { return dogImagesList; }

}
