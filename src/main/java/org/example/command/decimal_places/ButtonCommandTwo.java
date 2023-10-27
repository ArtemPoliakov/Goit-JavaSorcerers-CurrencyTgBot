package org.example.command.decimal_places;

import org.example.command.Command;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ButtonCommandTwo  implements Command {

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    }
    @Override
    public String getCommandIdentifier() {
        return "two";
    }
}
