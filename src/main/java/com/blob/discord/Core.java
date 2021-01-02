package com.blob.discord;

import com.blob.discord.listeners.CmdMessageListener;
import com.blob.discord.utilities.HtmlReader;
import com.blob.discord.utilities.JSONManager;
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

    public static void main(String[] args) throws LoginException, ParseException {

        JDABuilder builder = JDABuilder.createDefault("NzQxNzgwNzA3MTA5NzY1MTUw.Xy8jHg.lSPAcTKoBjgwVFR78_BDEGojPKk");

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.addEventListeners(new CmdMessageListener());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("TGU"));

        jda = builder.build();

        new JSONManager().initiateJson();
        new HtmlReader().onStartup();
        new CmdMessageListener().onStartup();

    }

    public JDA getJDA() { return jda; }
    public Logger getLogger() { return logger; }

}
