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

public class DecimalPlaces extends BotCommand {
    public DecimalPlaces() {
        super("decimal places", "Кількість знаків після коми");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        InlineKeyboardButton twoDecimal = InlineKeyboardButton.builder()
                .text("2")
                .callbackData("two")
                .build();
        InlineKeyboardButton threeDecimal = InlineKeyboardButton.builder()
                .text("3")
                .callbackData("three")
                .build();
        InlineKeyboardButton fourDecimal = InlineKeyboardButton.builder()
                .text("4")
                .callbackData("four")
                .build();
        InlineKeyboardButton backButton = InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData("command back")
                .build();
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboard(Arrays.asList(
                        Collections.singletonList(twoDecimal),
                        Collections.singletonList(threeDecimal),
                        Collections.singletonList(fourDecimal),
                        Collections.singletonList(backButton)
                ))
                .build();
        message.setText("Кількість знаків після коми: ");
        message.setReplyMarkup(keyboard);
        absSender.execute(message);
    }
}
