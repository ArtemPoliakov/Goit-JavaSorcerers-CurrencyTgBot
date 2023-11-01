package org.example.telegram.command.timeandzone.time;

import org.example.app.Database;
import org.example.app.messageprocessingandsendingpart.BotUser;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class AlertTimesResetCommand extends BotCommand {
    private Update update;
    private int times;

    public AlertTimesResetCommand(String times, Update update) {
        this();
        this.update = update;
        this.times = Integer.parseInt(times);
    }

    public AlertTimesResetCommand() {
        super("alertTimesCommand", "Command for processing digit buttons of alert times");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        botUser.setTimeOfSending(times);

        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        AlertTimesCommand alertTimesCommand = new AlertTimesCommand();
        InlineKeyboardMarkup inlineKeyboardMarkup = alertTimesCommand.getInlineKeyboardMarkup(botUser);
        EditMessageReplyMarkup edit = EditMessageReplyMarkup.builder()
                .chatId(chat.getId())
                .messageId(messageId)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
        absSender.execute(edit);
    }
}
