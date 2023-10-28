package org.example.command.timeAndZone;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

public class AlertTimes extends BotCommand {
    public AlertTimes() {
        super("alert times", "Час оповіщень");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton buttonNine = new KeyboardButton("9");
        KeyboardButton buttonTen = new KeyboardButton("10");
        KeyboardButton buttonEleven = new KeyboardButton("11");
        KeyboardButton buttonTwelve = new KeyboardButton("12");
        KeyboardButton buttonThirteen = new KeyboardButton("13");
        KeyboardButton buttonFourteen = new KeyboardButton("14");
        KeyboardButton buttonFifteen = new KeyboardButton("15");
        KeyboardButton buttonSixteen = new KeyboardButton("16");
        KeyboardButton buttonSeventeen = new KeyboardButton("17");
        KeyboardButton buttonEighteen = new KeyboardButton("18");
        KeyboardButton buttonTurnOffNotifications = new KeyboardButton("Виключити сповіщення🔕");

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(buttonNine);
        row1.add(buttonTen);
        row1.add(buttonEleven);
        row1.add(buttonTwelve);
        row1.add(buttonThirteen);
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(buttonFourteen);
        row2.add(buttonFifteen);
        row2.add(buttonSixteen);
        row2.add(buttonSeventeen);
        row2.add(buttonEighteen);
        keyboard.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(buttonTurnOffNotifications);
        keyboard.add(row3);

        message.setText("Оберіть годину або вимкніть повідомлення:");
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
        absSender.execute(message);
    }
}
