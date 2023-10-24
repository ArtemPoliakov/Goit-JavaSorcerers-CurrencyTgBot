package org.example.telegram;

import lombok.SneakyThrows;
import org.example.command.*;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Pattern;

import static org.example.telegram.BotConstants.BOT_NAME;
import static org.example.telegram.BotConstants.BOT_TOKEN;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private final Pattern commandPattern = Pattern.compile("/\\w+");

    public CurrencyTelegramBot() {
        register(new HelpCommand());
        register(new AlertTimes());
        register(new BackCommand());
        register(new BankCommand());
        register(new CurrencyCommand());
        register(new DecimalPlaces());
        register(new SettingsCommand());
        register(new StartCommand());
        registerDefaultAction((absSender, message) -> {
            SendMessage responseMessage = new SendMessage();
            responseMessage.setText("Ви ввели команду який бот не може розпізнати🤷🏼‍♂️\n" + "Це бот знає ось такі команди: \n" + "/start ~ /help");
            responseMessage.setChatId(message.getChatId());
            try {
                absSender.execute(responseMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
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
        System.out.println("Received update: " + update.toString());
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String action = callbackQuery.getData();
            if ("settings".equals(action)) {
                SettingsCommand settingsCommand = new SettingsCommand();
                settingsCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("back".equals(action)) {
                BankCommand bankCommand = new BankCommand();
                bankCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
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
        commandHelp(update);
    }

    private void commandHelp(Update update) throws TelegramApiException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            if ("/help".equals(message)) {
                HelpCommand helpCommand = new HelpCommand();
                helpCommand.execute(this, update.getMessage().getFrom(), update.getMessage().getChat(), null);
            }
            if (!commandPattern.matcher(message).matches()) {
                SendMessage responseMessage = new SendMessage();
                responseMessage.setText("Ви ввели текст який бот не може розпізнати🤷🏼‍♂️\n" + "Це бот знає ось такі команди: \n" + "/start ~ /help");
                responseMessage.setChatId(update.getMessage().getChatId());
                execute(responseMessage);
            }
        }
    }
}