package org.example.MessageProcessingAndSendingPart;


import org.example.app.Client;
import org.example.bank.Bank;
import org.example.bank.Currency;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class BotMessageProcessor {
    // TODO: delete main from here on production :)
    public static void main(String... args) throws InterruptedException {
        Client client = Client.getInstance();
        Thread.sleep(4000);
        new BotMessageProcessor().sendMessageToUser(BotUser.newDefaultUserById(2L), client.getBanks(), 1);
    }
    public void sendMessageToUser(BotUser botUser, Map<Bank.BankName, Bank> banksData, int amount){
        String messageText = buildMessageText(botUser, banksData, amount);
        System.out.println(messageText);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(botUser.getUserChatId());
        sendMessage.setText(messageText);
        InlineKeyboardButton buttonInfo = InlineKeyboardButton.builder()
                .text("Отримати інфо📚")
                .callbackData("info")
                .build();
        InlineKeyboardButton buttonSettings = InlineKeyboardButton.builder()
                .text("Налаштування⚙️")
                .callbackData("settings")
                .build();
        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                .keyboard(Collections.singletonList(
                        Arrays.asList(buttonInfo, buttonSettings)
                ))
                .build();
        sendMessage.setReplyMarkup(keyboard);
        //TODO: add sending logic
    }
    private String buildMessageText(BotUser botUser, Map<Bank.BankName, Bank> banksData, int amount){
        StringBuilder builder = new StringBuilder();
        for(Bank.BankName bankName: banksData.keySet()){
            if(botUser.getBanksMap().get(bankName)){
                builder.append(bankName.getMessage()+"\n");
                for(Currency currency: banksData.get(bankName).getCurrencyList()){
                    if(botUser.getCurrenciesMap().get(currency.getCurrencyName())){
                        builder.append(currency.getCurrencyName().getMessage()+"\n");
                        int signsAfterComma = botUser.getSignsAfterComma();
                        builder.append("Buy: "+ getUserFitAmount(currency.getBuyPrice(), signsAfterComma,amount)+"\n");
                        builder.append("Sell: "+getUserFitAmount(currency.getSellPrice(), signsAfterComma, amount)+"\n");
                    }
                }
                builder.append("\n");
            }
        }
        return String.valueOf(builder);
    }
    private String getUserFitAmount(double originValue,int decimalPlaces, int amount){
        BigDecimal bigDecimal = new BigDecimal(originValue);
        bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(amount));
        bigDecimal = bigDecimal.setScale(decimalPlaces, RoundingMode.HALF_UP);
        DecimalFormat df = new DecimalFormat("0." + "0".repeat(decimalPlaces));
        return df.format(bigDecimal.doubleValue());
    }
}
