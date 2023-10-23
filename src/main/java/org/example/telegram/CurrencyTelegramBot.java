package org.example.telegram;

import lombok.SneakyThrows;
import org.example.command.BackCommand;
import org.example.command.DecimalPlaces;
import org.example.command.SettingsCommand;
import org.example.command.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.telegram.BotConstants.BOT_NAME;
import static org.example.telegram.BotConstants.BOT_TOKEN;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {


    public CurrencyTelegramBot() {
        register(new StartCommand());
        register(new SettingsCommand());
        register(new BackCommand());
        register(new DecimalPlaces());
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    @SneakyThrows
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String action = callbackQuery.getData();
            if ("settings".equals(action)) {
                SettingsCommand settingsCommand = new SettingsCommand();
                settingsCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            }
            if ("back".equals(action)){
                BackCommand backCommand = new BackCommand();
                backCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            }
            if ("decimal places".equals(action)) {
                DecimalPlaces decimalPlacesCommand = new DecimalPlaces();
                decimalPlacesCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            }
        }
    }
}


