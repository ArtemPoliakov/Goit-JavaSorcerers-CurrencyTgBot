package org.example.command.timeAndZone;

import lombok.SneakyThrows;
import org.example.MessageProcessingAndSendingPart.BotUser;
import org.example.app.Database;
import org.example.command.DecimalPlaces;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ZoneResetCommand extends BotCommand {
    private Update update;
    private int zone;
    public ZoneResetCommand(String zone, Update update){
        this();
        this.update = update;
        this.zone = Integer.parseInt(zone);
    }
    public ZoneResetCommand(){
        super("zoneResetCommand", "Command for processing digit buttons of timezone");
    }
    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        botUser.setTimeZone(zone);

        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        ZoneCommand zoneCommand = new ZoneCommand();
        InlineKeyboardMarkup inlineKeyboardMarkup = zoneCommand.getInlineKeyboardMarkup(botUser);
        EditMessageReplyMarkup edit = EditMessageReplyMarkup.builder()
                .chatId(chat.getId())
                .messageId(messageId)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
        absSender.execute(edit);
    }
}
