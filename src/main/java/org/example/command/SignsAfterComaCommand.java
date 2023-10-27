package org.example.command;

import org.example.MessageProcessingAndSendingPart.BotUser;
import org.example.app.Database;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SignsAfterComaCommand extends BotCommand implements Command {
    private String signsAfterComa;
     public SignsAfterComaCommand(String signsAfterComa) {
         this();
         this.signsAfterComa = signsAfterComa;
    }
    public SignsAfterComaCommand(){
        super("decimal places", "Кількість знаків після коми:");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (!Database.getDatabase().getUsersMap().containsKey(chat.getId())){
            Database.getDatabase().getUsersMap().put(chat.getId(), BotUser.newDefaultUserById(chat.getId()));
        }
        BotUser botUser = Database.getDatabase().getUsersMap().get(chat.getId());
        int quantity = Integer.parseInt(signsAfterComa);
        botUser.setSignsAfterComma(quantity);
    }
}
