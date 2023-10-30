package org.example.command;

import lombok.SneakyThrows;
import org.example.MessageProcessingAndSendingPart.BotUser;
import org.example.app.Database;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SignsAfterComaCommand extends BotCommand {
    private Update update;
    private String signsAfterComa;

    public SignsAfterComaCommand(String signsAfterComa, Update update) {
        this();
        this.signsAfterComa = signsAfterComa;
        this.update = update;
    }

    public SignsAfterComaCommand() {
        super("decimal places", "Кількість знаків після коми:");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        int quantity = Integer.parseInt(signsAfterComa);
        botUser.setSignsAfterComma(quantity);

        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = chat.getId();
        DecimalPlaces dp = new DecimalPlaces();
        InlineKeyboardMarkup inlineKeyboardMarkup = dp.getInlineKeyboardMarkup(botUser);
        EditMessageReplyMarkup edit = EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
        absSender.execute(edit);
    }
}
