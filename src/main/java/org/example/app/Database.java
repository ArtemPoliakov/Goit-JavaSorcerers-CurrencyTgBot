package org.example.app;

import lombok.Getter;
import org.example.app.messageProcessingAndSendingPart.BotUser;

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
        return Database.getDatabase().getUsersMap().get(id);
    }
    public static void putUserById(long id, BotUser botUser){
        Database.getDatabase().getUsersMap().put(id, botUser);
    }
}
