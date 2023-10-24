package org.example.app;

import lombok.Getter;
import org.example.MessageProcessingAndSendingPart.BotUser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Database {
    private static final Database usersDatabase = new Database(new HashMap<>());
    @Getter
    private Map<Long, BotUser> usersMap;
    private Database(Map<Long, BotUser> usersMap){
        this.usersMap = usersMap;
    }
    public static Database getDatabase(){
        return usersDatabase;
    }
}
