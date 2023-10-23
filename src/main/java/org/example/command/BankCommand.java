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

public class BankCommand extends BotCommand {
    public BankCommand() {
        super("bank", "Банк");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        InlineKeyboardButton buttonNby = InlineKeyboardButton.builder()
                .text("НБУ")
                .callbackData("Nby") //поки так
                .build();
        InlineKeyboardButton buttonPrivatBank = InlineKeyboardButton.builder()
                .text("ПриватБанк")
                .callbackData("PrivatBank")
                .build();
        InlineKeyboardButton buttonMonoBank = InlineKeyboardButton.builder()
                .text("МоноБанк")
                .callbackData("MonoBank")
                .build();
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboard(Arrays.asList(
                    Collections.singletonList(buttonNby),
                    Collections.singletonList(buttonPrivatBank),
                    Collections.singletonList(buttonMonoBank)
        ))
                .build();
        message.setText("Оберіть який вас цікавить банк:");
        message.setReplyMarkup(keyboard);
        absSender.execute(message);
    }
}
