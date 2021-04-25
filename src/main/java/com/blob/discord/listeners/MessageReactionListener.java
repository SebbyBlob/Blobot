package com.blob.discord.listeners;

import com.blob.discord.utilities.Settings;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReactionListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (event.getChannel().getIdLong() != Settings.SelfRoleChannelId) return;
        if (event.getMember().getUser().isBot()) return;
        event.getChannel().retrieveMessageById(Settings.SelfRoleMsgId).queue(message -> {
            if (message == null) return;
            if (event.getReaction().getMessageIdLong() != message.getIdLong()) return;
            switch (event.getReactionEmote().getName()) {
                case ":grass_block:":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Settings.MinecraftRoleId)).queue();
                    break;
                case "1️⃣":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Settings.SheHerId)).queue();
                    event.getMember().modifyNickname(event.getMember().getEffectiveName() + " (she/her)").queue();
                    break;
                case "2️⃣":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Settings.HeHimId)).queue();
                    event.getMember().modifyNickname(event.getMember().getEffectiveName() + " (he/him)").queue();
                    break;
                case "3️⃣":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Settings.TheyThemId)).queue();
                    event.getMember().modifyNickname(event.getMember().getEffectiveName() + " (they/them)").queue();
                    break;
                case "4️⃣":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Settings.XeXemId)).queue();
                    event.getMember().modifyNickname(event.getMember().getEffectiveName() + " (xe/xem)").queue();
                    break;
                case "5️⃣":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Settings.VeVerId)).queue();
                    event.getMember().modifyNickname(event.getMember().getEffectiveName() + " (ve/ver)").queue();
                    break;
                case "6️⃣":
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(Settings.EyEmId)).queue();
                    event.getMember().modifyNickname(event.getMember().getEffectiveName() + " (ey/em)").queue();
                    break;
            }
        });
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        if (event.getChannel().getIdLong() != Settings.SelfRoleChannelId) return;
        if (event.getMember().getUser().isBot()) return;
        event.getChannel().retrieveMessageById(Settings.SelfRoleMsgId).queue(message -> {
            if (message == null) return;
            if (event.getReaction().getMessageIdLong() != message.getIdLong()) return;
            String oldNickname = event.getMember().getEffectiveName();
            switch (event.getReactionEmote().getName()) {
                case ":grass_block:":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(Settings.MinecraftRoleId)).queue();
                    break;
                case "1️⃣":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(Settings.SheHerId)).queue();
                    if (oldNickname.length() > 9
                            && oldNickname.substring(oldNickname.length() - 9).equalsIgnoreCase("(she/her)"))
                        event.getMember().modifyNickname(oldNickname.substring(0, oldNickname.length() - 9)).queue();
                    break;
                case "2️⃣":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(Settings.HeHimId)).queue();
                    if (oldNickname.length() > 8
                            && oldNickname.substring(oldNickname.length() - 8).equalsIgnoreCase("(he/him)"))
                        event.getMember().modifyNickname(oldNickname.substring(0, oldNickname.length() - 8)).queue();
                    break;
                case "3️⃣":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(Settings.TheyThemId)).queue();
                    if (oldNickname.length() > 11
                            && oldNickname.substring(oldNickname.length() - 11).equalsIgnoreCase("(they/them)"))
                        event.getMember().modifyNickname(oldNickname.substring(0, oldNickname.length() - 11)).queue();
                    break;
                case "4️⃣":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(Settings.XeXemId)).queue();
                    if (oldNickname.length() > 8
                            && oldNickname.substring(oldNickname.length() - 8).equalsIgnoreCase("(xe/xem)"))
                        event.getMember().modifyNickname(oldNickname.substring(0, oldNickname.length() - 8)).queue();
                    break;
                case "5️⃣":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(Settings.VeVerId)).queue();
                    if (oldNickname.length() > 8
                            && oldNickname.substring(oldNickname.length() - 8).equalsIgnoreCase("(ve/ver)"))
                        event.getMember().modifyNickname(oldNickname.substring(0, oldNickname.length() - 8)).queue();
                    break;
                case "6️⃣":
                    event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(Settings.EyEmId)).queue();
                    if (oldNickname.length() > 7
                            && oldNickname.substring(oldNickname.length() - 7).equalsIgnoreCase("(ey/em)"))
                        event.getMember().modifyNickname(oldNickname.substring(0, oldNickname.length() - 7)).queue();
                    break;
            }
        });
    }

    public void setupSelfRoles(JDA jda) {
        jda.getGuildById(Settings.TGUServerId).getTextChannelById(Settings.SelfRoleChannelId).retrieveMessageById(Settings.SelfRoleMsgId).queue(message -> {
            if (message == null) return;
            message.addReaction("grass_block:803708752564846642").queue();
            message.addReaction("1️⃣").queue();
            message.addReaction("2️⃣").queue();
            message.addReaction("3️⃣").queue();
            message.addReaction("4️⃣").queue();
            message.addReaction("5️⃣").queue();
            message.addReaction("6️⃣").queue();
        });
    }

}
