package org.example.command.timeAndZone;

import lombok.SneakyThrows;
import org.example.MessageProcessingAndSendingPart.BotUser;
import org.example.app.Database;
import org.example.projectUtils.UtilMethods;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

public class AlertTimesCommand extends BotCommand {
    public AlertTimesCommand() {
        super("alert times", "Час оповіщень");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText("Оберіть час розсилки повідомлень:");
        sendMessage.setReplyMarkup(getInlineKeyboardMarkup(botUser));
        absSender.execute(sendMessage);
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup(BotUser botUser) {
        String[] times = new String[]{"9", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        int counter = 0;
        while (counter < times.length) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                String txt = times[counter];
                int intEquivalent = Integer.parseInt(txt);
                row.add(UtilMethods.createButton(
                        botUser.getTimeOfSending() == intEquivalent ? "✅ " + txt : txt,
                        "alertTimesCommand_" + intEquivalent));
                counter++;
            }
            buttons.add(row);
        }
        buttons.add(List.of(UtilMethods.createButton
                (botUser.isProcessActive() ? "Вимкнути сповіщення🔕" : "✅ Вимкнути сповіщення🔕", "turnOff")));
        buttons.add(List.of(UtilMethods.createButton("Назад🔙", "command back")));
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
