package org.example.telegram.command.textCommands;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends BotCommand {
    private static final String HELP_MESSAGE =
             """
             Якщо вам потрібна допомога або є питання які ви хочете уточнити, напишіть їм👇🏻
             @ArtemPoliakovUA
             @fl1x3
             @R3qp1o
            """;
    public HelpCommand() {
        super("help", "Допомога");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText(HELP_MESSAGE);
        absSender.execute(message);
    }
}
