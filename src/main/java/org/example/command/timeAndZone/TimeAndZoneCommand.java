package org.example.command.timeAndZone;

import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
        InlineKeyboardButton time = InlineKeyboardButton
                .builder()
                .text("Час сповіщень🔔")
                .callbackData("alert times")
                .build();
        InlineKeyboardButton zone = InlineKeyboardButton
                .builder()
                .text("Часовий пояс\uD83C\uDF0E")
                .callbackData("zone")
                .build();
        InlineKeyboardButton backButton = InlineKeyboardButton.builder()
                .text("Назад🔙")
                .callbackData("back_fromOneStepFromSettings")
                .build();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(time, zone));
        buttons.add(List.of(backButton));
        return InlineKeyboardMarkup
                .builder()
                .keyboard(buttons)
                .build();
    }
}
