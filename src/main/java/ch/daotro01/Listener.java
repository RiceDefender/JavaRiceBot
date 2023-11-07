package ch.daotro01;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import org.json.JSONObject;

public class Listener extends ListenerAdapter {
    public static final String API_URL = "https://official-joke-api.appspot.com/random_joke";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (content.equals("!ping")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue();
        }
    }


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ping")) {
            //event.deferReply().queue();
            event.reply("pong").queue();
        } else if (event.getName().equals("rice")) {
            event.reply("do be cookin").queue();
        } else if (event.getName().equals("dadjoke")) {
            String dadJokeApi = "https://icanhazdadjoke.com/slack";
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(dadJokeApi)
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject jokeData = new JSONObject(responseBody);

                    String joke = jokeData.getJSONArray("attachments").getJSONObject(0).getString("text");
                    event.reply(joke).queue();
                } else {
                    event.reply("Failed to fetch a joke. Please try again later.").queue();
                }
            } catch (Exception e) {
                e.printStackTrace();
                event.reply("An error occurred while fetching a joke. Please try again later.").queue();
            }
        } else if (event.getName().equals("badjoke")) {
            String badJokeApi = "https://official-joke-api.appspot.com/random_joke";
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(badJokeApi)
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject jokeData = new JSONObject(responseBody);

                    String joke = jokeData.getString("setup"); // Replace with the actual JSON key for the joke
                    joke = joke + "\n" + jokeData.getString("punchline"); // Replace with the actual JSON key for the punchline

                    event.reply(joke).queue();
                } else {
                    event.reply("Failed to fetch a joke. Please try again later.").queue();
                }
            } catch (Exception e) {
                e.printStackTrace();
                event.reply("An error occurred while fetching a joke. Please try again later.").queue();
            }
        }
    }
}
