package org.example.telegram;

import lombok.SneakyThrows;
import org.example.command.*;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.telegram.BotConstants.BOT_NAME;
import static org.example.telegram.BotConstants.BOT_TOKEN;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    public CurrencyTelegramBot() {
        register(new AlertTimes());
        register(new BackCommand());
        register(new BankCommand());
        register(new CurrencyCommand());
        register(new DecimalPlaces());
        register(new HelpCommand());
        register(new SettingsCommand());
        register(new StartCommand());
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
            } else if ("back".equals(action)) {
                System.out.println("---");
            } else if ("decimal places".equals(action)) {
                DecimalPlaces decimalPlacesCommand = new DecimalPlaces();
                decimalPlacesCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("bank".equals(action)) {
                BankCommand bankCommand = new BankCommand();
                bankCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("alert times".equals(action)) {
                AlertTimes alertTimesCommand = new AlertTimes();
                alertTimesCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            }

        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            if ("/help".equals(message)) {
                HelpCommand helpCommand = new HelpCommand();
                helpCommand.execute(this, update.getMessage().getFrom(), update.getMessage().getChat(), null);
            }
        }
    }
}

