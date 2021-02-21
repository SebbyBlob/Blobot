package com.blob.discord.utilities;

import com.blob.discord.Core;
import net.dv8tion.jda.api.entities.Message;

import java.util.*;

public class TUtils {

    //Runs on bot Startup
    public void onStartup() {
        //Makes sure the last t sent is not null
        if (!(new BlameSebJSONManager().readJsonFile()[4] == null)) {
            Message lastSentT = Core.getJDA().getTextChannelById("770731649569783829").retrieveMessageById((Long) new BlameSebJSONManager().readJsonFile()[4]).complete();
            readTAfterMsg(lastSentT);
        } else {
            //Reads all t's in #t
            readAllT();
        }
    }

    //Reads the first 100 t's in #t
    public void readAllT() {
        Core.getJDA().getTextChannelById(770731649569783829L).getHistoryFromBeginning(100).queue(messageHistory -> {
            for (Message msg : messageHistory.getRetrievedHistory()) {

                if ()
            }
                Message msg;
                //Runs through the loop 100 times
                for (int i = 0; i < 100; i++) {
                    try {
                        msg = messageHistory.getRetrievedHistory().get(i);
                    } catch (IndexOutOfBoundsException exception) {
                        getLastSentT();
                        return;
                    }
                    //Makes sure the message is a "t"
                    if (msg.getContentRaw().equals("t")) {
                        new TDataJSONManager().addTDataJsonValue(msg.getAuthor().getId());
                    } else {
                        Core.getLogger().info("Non-t found in #t, message link: " + msg.getJumpUrl());
                        Core.getJDA().getTextChannelsByName("general", false).get(0).sendMessage("Non-t found in #t, message link: " + msg.getJumpUrl()).queue();
                    }
                    if (i == 99) {
                        readTAfterMsg(messageHistory.getRetrievedHistory().get(0));
                    }
                }

        });
    }

    //Reads the first 100 t's after a certain message
    public void readTAfterMsg(Message messageAfter) {
        Core.getJDA().getTextChannelById("770731649569783829").getHistoryAfter(messageAfter, 100).queue(messageHistory -> {
            if (!messageHistory.isEmpty()) {
                Message msg;
                //Runs through the loop 100 times
                for (int i = 0; i < 100; i++) {
                    try {
                        msg = messageHistory.getRetrievedHistory().get(i);
                    } catch (IndexOutOfBoundsException exception) {
                        getLastSentT();
                        return;
                    }
                    //Makes sure the message is a "t"
                    if (msg.getContentRaw().equals("t")) {
                        new TDataJSONManager().addTDataJsonValue(msg.getAuthor().getId());
                    } else {
                        Core.getLogger().info("Non-t found in #t, message link: " + msg.getJumpUrl());
                        Core.getJDA().getTextChannelsByName("general", false).get(0).sendMessage("Non-t found in #t, message link: " + msg.getJumpUrl()).queue();
                    }
                    if (i == 99) {
                        readTAfterMsg(messageHistory.getRetrievedHistory().get(0));
                    }
                }
            }
        });
    }

    //Sets the last sent t in #t into tdata.json file
    private void getLastSentT() {
        Core.getJDA().getTextChannelById("770731649569783829").getHistory().retrievePast(1)
                .map(messages -> messages.get(0))
                .queue(message -> {
                    new BlameSebJSONManager().setJsonValue(4, message.getIdLong());
                });
    }

    //Sorts the top users who have said "t" in #t into a list
    public List tSorter() {

        HashMap<String, Long> jsonFileHashMap = new HashMap<>();

        //Puts all said "t"'s and their author into the list
        for (Object objKey : new TDataJSONManager().getTDataJsonObject().keySet()) {
            String key = (String) objKey;

            jsonFileHashMap.put(key, new TDataJSONManager().readJsonFile(key));

        }

        List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(jsonFileHashMap.entrySet());
        //Sorts list highest to lowest
        list.sort(new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return list;
    }

}
