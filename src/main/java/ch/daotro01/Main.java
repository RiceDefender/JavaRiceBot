package ch.daotro01;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        JDABuilder builder = JDABuilder.createDefault(args[0]);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.AUTO_MODERATION_CONFIGURATION,
                GatewayIntent.GUILD_PRESENCES,GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.enableCache(CacheFlag.ACTIVITY);
        builder.addEventListeners(new Listener());
        builder.setActivity(Activity.playing("VIBIN"));
        builder.build();
        //builder.upsertCommand("ping", "ping command").queue();
        JDA jda = builder.build();

        jda.updateCommands().addCommands(
                Commands.slash("ping", "ping command"),
                Commands.slash("rice", "rice command"),
                Commands.slash("dadjoke", "dad joke (the jokes are really bad...)"),
                Commands.slash("badjoke", "bad joke (the jokes are really bad...)")
        ).queue();
    }
}
