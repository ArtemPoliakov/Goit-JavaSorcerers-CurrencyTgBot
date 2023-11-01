package org.example.telegram.command.signs;

import org.example.app.Database;
import org.example.app.messageprocessingandsendingpart.BotUser;
import org.example.projectutils.UtilMethods;
import lombok.SneakyThrows;
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

public class DecimalPlaces extends BotCommand {
    private Update update;
    public DecimalPlaces(Update update){
        this();
        this.update = update;
    }
    public DecimalPlaces() {
        super("decimal places", "Кількість знаків після коми:");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotUser botUser = Database.getUserById(chat.getId());
        EditMessageReplyMarkup message = EditMessageReplyMarkup
                .builder()
                .chatId(chat.getId())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .replyMarkup(getInlineKeyboardMarkup(botUser))
                .build();
        absSender.execute(message);
    }

    public InlineKeyboardMarkup getInlineKeyboardMarkup(BotUser botUser) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (int i = 2; i < 5; i++) {
            String text = botUser.getSignsAfterComma() == i ? "✅ " + i : String.valueOf(i);
            InlineKeyboardButton button = UtilMethods.createButton(text, "signsAfterComma_" + i);
            buttons.add(List.of(button));
        }
        InlineKeyboardButton backButton =
                UtilMethods.createButton("Назад🔙", "back_fromOneStepFromSettings");
        buttons.add(List.of(backButton));
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
