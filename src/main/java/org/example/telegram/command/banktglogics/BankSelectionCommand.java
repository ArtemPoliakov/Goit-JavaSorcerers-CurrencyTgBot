package org.example.telegram.command.banktglogics;

import org.example.app.Database;
import org.example.app.bank.Bank;
import org.example.app.messageprocessingandsendingpart.BotUser;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class BankSelectionCommand extends BotCommand {
    private String name;
    private Update update;

    public BankSelectionCommand(String name, Update update) {
        this();
        this.name = name;
        this.update = update;
    }

    public BankSelectionCommand() {
        super("bankSelectionCommand", "Command for managing user banks");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        BanksCommand banksCommand = new BanksCommand();
        Bank.BankName bankEnum = banksCommand.convertStringToBankEnum(name);
        botUser.getBanksMap().put(bankEnum, !botUser.getBanksMap().get(bankEnum));
        InlineKeyboardMarkup inlineKeyboardMarkup = banksCommand.getInlineKeyboardMarkup(botUser);
        EditMessageReplyMarkup edit = EditMessageReplyMarkup.builder()
                .chatId(chat.getId())
                .messageId(messageId)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
        absSender.execute(edit);
    }
}
