package org.example;

import org.example.app.Client;
import org.example.telegram.TelegramBotService;


public class TelegramBot {
    public static void main(String[] args) throws InterruptedException {
        Client.getInstance();
        TelegramBotService telegramBotService = new TelegramBotService();
    }
}
