package org.example.telegram.command.bankTgLogics;

import org.example.app.Database;
import org.example.app.bank.Bank;
import org.example.app.messageProcessingAndSendingPart.BotUser;
import org.example.projectUtils.UtilMethods;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

public class BanksCommand extends BotCommand {
    private Update update;
    public BanksCommand(Update update){
        this();
        this.update = update;
    }
    public BanksCommand(){
        super("banksCommand", "Command for getting banks menu");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        EditMessageReplyMarkup message = EditMessageReplyMarkup
                .builder()
                .chatId(chat.getId())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .replyMarkup(getInlineKeyboardMarkup(botUser))
                .build();
        absSender.execute(message);
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
        buttons.add(List.of(UtilMethods.createButton("Назад\uD83D\uDD19", "back_fromOneStepFromSettings")));
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
