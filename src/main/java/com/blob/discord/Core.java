package com.blob.discord;

import com.blob.discord.listeners.*;
import com.blob.discord.utilities.BlameSebJSONManager;
import com.blob.discord.utilities.HtmlReader;
import com.blob.discord.utilities.TDataJSONManager;
import com.blob.discord.utilities.TUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Core {

    public final Long AutoVCGuildId = 530904600119869450L;
    public final Long AutoVCCategoryId = 809518946079211600L;

    private static JDA jda;
    private static Logger logger = LoggerFactory.getLogger("Blobot");

    public static void main(String[] args) throws LoginException, InterruptedException {

        //Checks if there is a bot token provided
        if (args.length != 1) {
            Core.getLogger().error("No bot token provided! Shutting down...");
            System.exit(1);
        }

        JDABuilder builder = JDABuilder.createDefault(args[0]);

        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("TGU"));

        jda = builder.build();
        jda.awaitReady();

        jda.addEventListener(new CmdMessageListener());
        jda.addEventListener(new MessageListener());
        jda.addEventListener(new VoiceJoinListener());
        jda.addEventListener(new VoiceLeaveListener());
        jda.addEventListener(new VoiceMoveListener());
        jda.addEventListener(new GuildUserLeaveListener());
        jda.addEventListener(new GuildUserJoinListener());

        new CmdMessageListener().onStartup();
        new BlameSebJSONManager().initiateJson();
        new TDataJSONManager().initiateJson();
        new TUtils().onStartup();
        VoiceJoinListener.getInstance().onStartup();
        new HtmlReader().onStartup();

    }

    public static JDA getJDA() { return jda; }
    public static Logger getLogger() { return logger; }
}
