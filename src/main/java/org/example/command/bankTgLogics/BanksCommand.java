package org.example.command.bankTgLogics;

import lombok.SneakyThrows;
import org.example.messageProcessingAndSendingPart.BotUser;
import org.example.app.Database;
import org.example.bank.Bank;
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

public class BanksCommand extends BotCommand {
    public BanksCommand() {
        super("banksCommand", "Command for getting banks menu");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Оберіть банки, що вас цікавлять:");
        sendMessage.setChatId(chat.getId());
        sendMessage.setReplyMarkup(getInlineKeyboardMarkup(botUser));
        absSender.execute(sendMessage);
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup(BotUser botUser) {
        List<String> allNamesList = List.of("MONO", "PRIVAT", "NBU");
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (String name : allNamesList) {
            Bank.BankName bankEnum = convertStringToBankEnum(name);
            String ukrText = convertEngToUkr(name);
            buttons.add(List.of(UtilMethods.createButton(botUser.getBanksMap().get(bankEnum)
                    ? "✅ " + ukrText : ukrText, "bankSelection_" + name)));
        }
        return InlineKeyboardMarkup
                .builder()
                .keyboard(buttons)
                .build();
    }

    public Bank.BankName convertStringToBankEnum(String name) {
        return switch (name) {
            case "MONO" -> Bank.BankName.MONO;
            case "PRIVAT" -> Bank.BankName.PRIVAT;
            case "NBU" -> Bank.BankName.NBU;
            default -> Bank.BankName.UNDEFINED_BANK;
        };
    }

    private String convertEngToUkr(String engName) {
        return switch (engName) {
            case "MONO" -> "Монобанк\uD83C\uDFE6";
            case "PRIVAT" -> "Приватбанк\uD83C\uDFE6";
            case "NBU" -> "НБУ\uD83C\uDFE6";
            default -> "Помилка(не тицяти)";
        };
    }
}
