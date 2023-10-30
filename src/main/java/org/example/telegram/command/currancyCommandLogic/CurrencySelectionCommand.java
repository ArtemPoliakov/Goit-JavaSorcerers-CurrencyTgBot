package org.example.telegram.command.currancyCommandLogic;

import lombok.SneakyThrows;
import org.example.app.Database;
import org.example.app.bank.Currency;
import org.example.app.messageProcessingAndSendingPart.BotUser;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CurrencySelectionCommand extends BotCommand {
    private String name;
    private Update update;

    public CurrencySelectionCommand(String name, Update update) {
        this();
        this.name = name;
        this.update = update;
    }

    public CurrencySelectionCommand() {
        super("currencySelectionCommand", "Currency for managing user banks");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        CurrencyCommand currencyCommand = new CurrencyCommand();
        Currency.CurrencyName currencyName = currencyCommand.convertStringToCurrencyEnum(name);
        botUser.getCurrenciesMap().put(currencyName, !botUser.getCurrenciesMap().get(currencyName));
        InlineKeyboardMarkup inlineKeyboardMarkup = currencyCommand.getInlineKeyboardMarkup(botUser);
        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                .chatId(chat.getId())
                .messageId(messageId)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
        absSender.execute(editMessageReplyMarkup);
    }
}
