package org.example.command;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

public class DecimalPlaces extends BotCommand {
    public DecimalPlaces() {
        super("decimal places", "Кількість знаків після коми:");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (int i = 2; i < 5; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(String.valueOf(i))
                    .callbackData("decimal places_" + i)
                    .build();
            buttons.add(List.of(button));
        }
        InlineKeyboardButton backButton = InlineKeyboardButton.builder()
                .text("Назад🔙")
                .callbackData("command back")
                .build();
        buttons.add(List.of(backButton));
        message.setText("Кількість знаків після коми:");
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                        .build();
        message.setReplyMarkup(keyboard);
        absSender.execute(message);
    }
}
