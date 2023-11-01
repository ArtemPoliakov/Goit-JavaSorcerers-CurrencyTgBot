package org.example.telegram.command.textcommands;

import org.example.projectutils.UtilMethods;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.Collections;

public class StartCommand extends BotCommand {
    private static final String START_MESSAGE =
            """
               Вітаю🙌🏻
               Я відслідковую актуальний курс валют💵
               Чим можу бути корисний?
            """;
    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setText(START_MESSAGE);
        message.setChatId(chat.getId());
        message.setReplyMarkup(getInlineKeyboardMarkup());
        absSender.execute(message);
    }
    public InlineKeyboardMarkup getInlineKeyboardMarkup(){
        InlineKeyboardButton buttonInfo = UtilMethods.createButton("Отримати інфо📚", "info");
        InlineKeyboardButton buttonSettings = UtilMethods.createButton("Налаштування⚙️", "settings");
        return InlineKeyboardMarkup.builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(buttonInfo, buttonSettings)
                ))
                .build();
    }
}
