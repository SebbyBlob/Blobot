package com.blob.discord;

import com.blob.discord.listeners.CmdMessageListener;
import com.blob.discord.listeners.MessageListener;
import com.blob.discord.utilities.BlameSebJSONManager;
import com.blob.discord.utilities.TDataJSONManager;
import com.blob.discord.utilities.TUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.text.ParseException;

public class Core {

    private static JDA jda;
    private static Logger logger = LoggerFactory.getLogger("Blobot");

    public static void main(String[] args) throws LoginException, ParseException, InterruptedException {

        JDABuilder builder = JDABuilder.createDefault("NzQxNzgwNzA3MTA5NzY1MTUw.Xy8jHg.XnfQfT7FYZMbpB7y6zc3jxjTj3U");

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.addEventListeners(new CmdMessageListener());
        builder.addEventListeners(new MessageListener());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("TGU"));

        jda = builder.build();
        jda.awaitReady();

        new CmdMessageListener().onStartup();
        new BlameSebJSONManager().initiateJson();
        new TDataJSONManager().initiateJson();
        new TUtils().onStartup();
        //new HtmlReader().onStartup();

    }

    public static JDA getJDA() { return jda; }
    public static Logger getLogger() { return logger; }

}
