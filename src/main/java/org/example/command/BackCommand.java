package org.example.command;

import lombok.SneakyThrows;
import org.example.MessageProcessingAndSendingPart.BotUser;
import org.example.app.Database;
import org.example.command.timeAndZone.TimeAndZoneCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.swing.text.html.InlineView;


public class BackCommand extends BotCommand {
    private String fromWhom;
    private Update update;
    public BackCommand(String fromWhom, Update update){
        this();
        this.fromWhom = fromWhom;
        this.update = update;
    }
    public BackCommand() {
        super("back", "Назад🔙");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        InlineKeyboardMarkup previous = new InlineKeyboardMarkup();
        if(fromWhom.equals("fromOneStepFromSettings")){
            previous = new SettingsCommand().getInlineKeyboardMarkup();
        } else if(fromWhom.equals("fromTimeOrZoneSmallLayout")){
            previous = new TimeAndZoneCommand().getInlineKeyboardMarkup();
        } else if(fromWhom.equals("fromActuallySettings")){
            previous = new StartCommand().getInlineKeyboardMarkup();
        }

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
