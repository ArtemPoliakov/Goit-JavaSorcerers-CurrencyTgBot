package org.example.projectUtils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class UtilMethods {
    public static InlineKeyboardButton createButton(String text, String callBackData) {
        return InlineKeyboardButton
                .builder()
                .text(text)
                .callbackData(callBackData)
                .build();
    }
}
