package com.blob.discord;

import com.blob.discord.listeners.*;
import com.blob.discord.managers.BlameSebJSONManager;
import com.blob.discord.managers.CommandManager;
import com.blob.discord.managers.TDataJSONManager;
import com.blob.discord.utilities.Settings;
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

    private static JDA jda;
    private static Logger logger = LoggerFactory.getLogger("Blobot");

    public static void main(String[] args) {
        //Checks if there is a bot token provided
        if (args.length != 1) {
            Core.getLogger().error("No bot token provided! Shutting down...");
            System.exit(1);
        }

        try {
            //Attempts to build JDA
            jda = JDABuilder.createDefault(args[0])
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS)
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS)
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.watching("TGU"))
                    .build()
                    .awaitReady();
        } catch (LoginException | InterruptedException exception) {
            logger.error("Failed to connect to discord (Invalid token?)! Shutting down...");
            System.exit(1);
        }

        //Adds event listeners
        jda.addEventListener(new CmdMessageListener(),
                new MessageListener(),
                new VoiceJoinListener(),
                new VoiceLeaveListener(),
                new VoiceMoveListener(),
                new GuildUserLeaveListener(),
                new GuildUserJoinListener(),
                new ConversationMessageListener(),
                new MessageReactionListener(),
                new MessageEditListener());

        //Initiation
        new CmdMessageListener().onStartup();
        new BlameSebJSONManager().initiateJson();
        new TDataJSONManager().initiateJson();
        new TUtils().onStartup(jda.getGuildById(Settings.TGUServerId));
        VoiceJoinListener.getInstance().onStartup();
        CommandManager.getInstance().onStartup();
        new MessageReactionListener().setupSelfRoles(jda);
        //new HtmlReader().onStartup();
    }

    public static JDA getJDA() { return jda; }
    public static Logger getLogger() { return logger; }
}
