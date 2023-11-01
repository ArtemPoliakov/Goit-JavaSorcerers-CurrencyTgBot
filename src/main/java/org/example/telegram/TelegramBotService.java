package org.example.telegram;

import org.example.app.Client;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotService {
    public TelegramBotService() {
        CurrencyTelegramBot currencyTelegramBot = CurrencyTelegramBot.getInstance();
        Client.getInstance();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTelegramBot);
        } catch (TelegramApiException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
