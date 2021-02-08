package com.blob.discord.utilities;

import com.blob.discord.Core;
import net.dv8tion.jda.api.entities.Message;

import java.util.*;

public class TUtils {

    public void onStartup() {
        if (!(new BlameSebJSONManager().readJsonFile()[4] == null)) {
            Message lastSentT = Core.getJDA().getTextChannelById("770731649569783829").retrieveMessageById((Long) new BlameSebJSONManager().readJsonFile()[4]).complete();
            readTAfterMsg(lastSentT);
        } else {
            readAllT();
        }
    }

    public void readAllT() {
        Core.getJDA().getTextChannelById(770731649569783829L).getHistoryFromBeginning(100).queue(messageHistory -> {
            if (!messageHistory.isEmpty()) {
                Message msg;
                for (int i = 0; i < 100; i++) {
                    try {
                        msg = messageHistory.getRetrievedHistory().get(i);
                    } catch (IndexOutOfBoundsException exception) {
                        getLastSentT();
                        return;
                    }
                    if (msg.getContentRaw().equals("t")) {
                        new TDataJSONManager().addTDataJsonValue(msg.getAuthor().getId());
                    } else {
                        Core.getLogger().info("Non-t found in #t, message link: " + msg.getJumpUrl());
                    }
                    if (i == 99) {
                        readTAfterMsg(messageHistory.getRetrievedHistory().get(0));
                    }
                }
            }
        });
    }

    public void readTAfterMsg(Message messageAfter) {
        Core.getJDA().getTextChannelById("770731649569783829").getHistoryAfter(messageAfter, 100).queue(messageHistory -> {
            if (!messageHistory.isEmpty()) {
                Message msg;
                for (int i = 0; i < 100; i++) {
                    try {
                        msg = messageHistory.getRetrievedHistory().get(i);
                    } catch (IndexOutOfBoundsException exception) {
                        getLastSentT();
                        return;
                    }
                    if (msg.getContentRaw().equals("t")) {
                        new TDataJSONManager().addTDataJsonValue(msg.getAuthor().getId());
                    } else {
                        Core.getLogger().info("Non-t found in #t, message link: " + msg.getJumpUrl());
                    }
                    if (i == 99) {
                        readTAfterMsg(messageHistory.getRetrievedHistory().get(0));
                    }
                }
            }
        });
    }

    private void getLastSentT() {
        Core.getJDA().getTextChannelById("770731649569783829").getHistory().retrievePast(1)
                .map(messages -> messages.get(0))
                .queue(message -> {
                    new BlameSebJSONManager().setJsonValue(4, message.getIdLong());
                });
    }

    public List tSorter() {

        HashMap<String, Long> jsonFileHashMap = new HashMap<>();

        for (Object objKey : new TDataJSONManager().getTDataJsonObject().keySet()) {
            String key = (String) objKey;

            jsonFileHashMap.put(key, new TDataJSONManager().readJsonFile(key));

        }

        List<Map.Entry<String, Long>> list = new LinkedList<Map.Entry<String, Long>>(jsonFileHashMap.entrySet());
        list.sort(new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return list;
    }

}
