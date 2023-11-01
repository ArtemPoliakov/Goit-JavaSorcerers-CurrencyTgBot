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

public class TurnOffCommand extends BotCommand {
    private Update update;


    public TurnOffCommand(Update update) {
        this();
        this.update = update;

    }

    public TurnOffCommand() {
        super("turnOff", "Вимкнути сповіщення🔕");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        botUser.killUserSendingProcessAndResetTime();

        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        AlertTimesCommand alertTimesCommand = new AlertTimesCommand();
        InlineKeyboardMarkup inlineKeyboardMarkup = alertTimesCommand.getInlineKeyboardMarkup(botUser);
        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                .replyMarkup(inlineKeyboardMarkup)
                .messageId(messageId)
                .chatId(chat.getId())
                .build();
        absSender.execute(editMessageReplyMarkup);
    }
}
