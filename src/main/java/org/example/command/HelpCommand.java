package org.example.command;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends BotCommand {
    public HelpCommand() {
        super("help", "Допомога");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
            SendMessage message = new SendMessage();
            message.setChatId(chat.getId());
            message.setText("Якщо вам потрібна допомога або є питання які ви хочете уточнити, напишіть їм👇🏻\n"
                    + "@ArtemPoliakovUA\n"
                    + "@fl1x3\n"
                    + "@R3qp1o\n");
            absSender.execute(message);
    }
}
