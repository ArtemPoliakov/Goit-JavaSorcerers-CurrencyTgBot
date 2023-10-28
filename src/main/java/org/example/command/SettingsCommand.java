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

public class SettingsCommand extends BotCommand {
    public SettingsCommand() {
        super("settings", "Налаштування");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        InlineKeyboardButton decimalPlaces = InlineKeyboardButton.builder()
                .text("Кількість знаків після коми✏️")
                .callbackData("signsAfterComa")
                .build();
        InlineKeyboardButton bank = InlineKeyboardButton.builder()
                .text("Банк💰")
                .callbackData("bank")
                .build();
        InlineKeyboardButton currency = InlineKeyboardButton.builder()
                .text("Валюти💲")
                .callbackData("currency")
                .build();
        InlineKeyboardButton timeAndZone = InlineKeyboardButton.builder()
                .text("Час та часовий пояс🔔\uD83C\uDF0E")
                .callbackData("TimeAndZone")
                .build();
        InlineKeyboardButton back = InlineKeyboardButton.builder()
                .text("Назад🔙")
                .callbackData("back")
                .build();
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboard(Arrays.asList(
                        Collections.singletonList(decimalPlaces),
                        Collections.singletonList(bank),
                        Collections.singletonList(currency),
                        Collections.singletonList(timeAndZone),
                        Collections.singletonList(back)
                ))
                .build();

        message.setText("Оберіть налаштування:");
        message.setReplyMarkup(keyboard);
        absSender.execute(message);
    }
}
