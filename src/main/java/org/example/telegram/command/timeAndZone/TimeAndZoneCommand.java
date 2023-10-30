package org.example.telegram.command.timeAndZone;

import lombok.SneakyThrows;
import org.example.projectUtils.UtilMethods;
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

public class TimeAndZoneCommand extends BotCommand {
    private Update update;
    public TimeAndZoneCommand(Update update){
        this();
        this.update = update;
    }
    public TimeAndZoneCommand(){
        super("TimeAndZone", "Time and zone command");
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        EditMessageReplyMarkup message = EditMessageReplyMarkup
                .builder()
                .chatId(chat.getId())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .replyMarkup(getInlineKeyboardMarkup())
                .build();
        absSender.execute(message);
    }
    public InlineKeyboardMarkup getInlineKeyboardMarkup(){
        InlineKeyboardButton time = UtilMethods.createButton("Час сповіщень🔔", "alertTimes");
        InlineKeyboardButton zone = UtilMethods.createButton("Часовий пояс\uD83C\uDF0E", "zone");
        InlineKeyboardButton backButton =
                UtilMethods.createButton("Назад🔙", "back_fromOneStepFromSettings");

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(time, zone));
        buttons.add(List.of(backButton));
        return InlineKeyboardMarkup
                .builder()
                .keyboard(buttons)
                .build();
    }
}
