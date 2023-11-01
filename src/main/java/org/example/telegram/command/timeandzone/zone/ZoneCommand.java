package org.example.telegram.command.timeandzone.zone;

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

public class ZoneCommand extends BotCommand {
    private Update update;
    public ZoneCommand(Update update){
        this();
        this.update = update;
    }
    public ZoneCommand(){
        super("ZoneCommand", "Command for timezone management");
    }

    @SneakyThrows
    @Override
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
        String[] zones = new String[]{"+0", "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10", "+11",
                "+12", "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10", "-11", "-12"};
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        int counter = 0;
        while (counter < zones.length) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                String s = zones[counter];
                int intEquivalent = Integer.parseInt(s.replace("+", ""));
                row.add(UtilMethods.createButton(
                        botUser.getTimeZone() == intEquivalent ? "✅ " + s : s,
                        "zoneResetCommand_" + intEquivalent));
                counter++;
            }
            buttons.add(row);
        }
        buttons.add(List.of(UtilMethods.createButton("Назад🔙", "back_fromTimeOrZoneSmallLayout")));
        return InlineKeyboardMarkup.builder().keyboard(buttons).build();
    }
}
