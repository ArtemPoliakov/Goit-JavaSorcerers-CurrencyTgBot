package org.example.app;

import lombok.Getter;
import org.example.messageProcessingAndSendingPart.BotUser;

import java.util.HashMap;
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
    public static BotUser getUserById(long id){
        if (!Database.getDatabase().getUsersMap().containsKey(id)){
            Database.getDatabase().getUsersMap().put(id, BotUser.newDefaultUserById(id));
        }
        BotUser botUser = Database.getDatabase().getUsersMap().get(id);
        return botUser;
    }
    public static void putUserById(long id, BotUser botUser){
        Database.getDatabase().getUsersMap().put(id, botUser);
    }
}
