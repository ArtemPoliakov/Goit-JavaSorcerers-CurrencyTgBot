package org.example.telegram.command;

import org.example.telegram.command.startsetupcommands.SettingsCommand;
import org.example.telegram.command.textcommands.StartCommand;
import org.example.telegram.command.timeandzone.TimeAndZoneCommand;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;


public class BackCommand extends BotCommand {
    private String fromWhom;
    private Update update;
    public BackCommand(String fromWhom, Update update){
        this();
        this.fromWhom = fromWhom;
        this.update = update;
    }
    public BackCommand() {
        super("command back", "Назад🔙");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        InlineKeyboardMarkup previous =
                switch(fromWhom){
                  case "fromOneStepFromSettings" -> new SettingsCommand().getInlineKeyboardMarkup();
                  case "fromTimeOrZoneSmallLayout" -> new TimeAndZoneCommand().getInlineKeyboardMarkup();
                  case "fromActuallySettings" -> new StartCommand().getInlineKeyboardMarkup();
                  default -> new InlineKeyboardMarkup();
        };

        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        EditMessageReplyMarkup edit = EditMessageReplyMarkup
                .builder()
                .chatId(chat.getId())
                .messageId(messageId)
                .replyMarkup(previous)
                .build();
        absSender.execute(edit);
    }
}
