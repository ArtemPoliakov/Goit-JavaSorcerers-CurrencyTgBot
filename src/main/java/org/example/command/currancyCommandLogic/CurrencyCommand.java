package org.example.command.currancyCommandLogic;

import lombok.SneakyThrows;
import org.example.app.Database;
import org.example.bank.Bank;
import org.example.bank.Currency;
import org.example.messageProcessingAndSendingPart.BotUser;
import org.example.projectUtils.UtilMethods;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

public class CurrencyCommand extends BotCommand {
    public CurrencyCommand() {
        super("currencyCommand", "Command for getting currency menu");
    }


    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Оберіть валюти які вас цікавлять:");
        sendMessage.setChatId(chat.getId());
        sendMessage.setReplyMarkup(getInlineKeyboardMarkup(botUser));
        absSender.execute(sendMessage);

    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup(BotUser botUser) {
        List<String> textButton = List.of("USD", "EUR");
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (String name : textButton) {
            Currency.CurrencyName enumName = convertStringToCurrencyEnum(name);
            boolean need = botUser.getCurrenciesMap().get(enumName);
            String text = need ? "✅ " + name : name;
            buttons.add(List.of(
                    UtilMethods.createButton(text, "currencySelection_" + name)));
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    public Currency.CurrencyName convertStringToCurrencyEnum(String name) {
        return
                switch (name) {
                    case "USD" -> Currency.CurrencyName.USD;
                    case "EUR" -> Currency.CurrencyName.EUR;
                    default -> Currency.CurrencyName.UNDEFINED;
                };
    }
}
