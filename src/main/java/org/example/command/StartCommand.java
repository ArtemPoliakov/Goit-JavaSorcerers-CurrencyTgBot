package org.example.command;

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
    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        var text = "Вітаю🙌🏻\nЯ відслідковую актуальний курс валют💵\nЧим можу бути корисний?";
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chat.getId());

        message.setReplyMarkup(getInlineKeyboardMarkup());
        absSender.execute(message);
    }
    public InlineKeyboardMarkup getInlineKeyboardMarkup(){
        InlineKeyboardButton buttonInfo = InlineKeyboardButton.builder()
                .text("Отримати інфо📚")
                .callbackData("info")
                .build();
        InlineKeyboardButton buttonSettings = InlineKeyboardButton.builder()
                .text("Налаштування⚙️")
                .callbackData("settings")
                .build();
        return InlineKeyboardMarkup.builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(buttonInfo, buttonSettings)
                ))
                .build();
    }
}
