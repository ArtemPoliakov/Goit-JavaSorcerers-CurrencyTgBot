package org.example.app.messageprocessingandsendingpart;


import org.example.app.bank.Currency;
import lombok.SneakyThrows;
import org.example.app.bank.Bank;
import org.example.telegram.command.textcommands.StartCommand;
import org.example.telegram.CurrencyTelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

public class BotMessageProcessor {
    private static final String NO_BANK_SELECTED_MESSAGE = """
            Здається, ви не обрали жодного банку 😢
            Перевірте, будь ласка, свої Налаштування⚙️
            """;
    private static final String NO_CURRENCY_SELECTED_MESSAGE = """
            Здається, ви не обрали жодної валюти 😢
            Перевірте, будь ласка, свої Налаштування⚙️
            """;
    @SneakyThrows
    public void sendMessageToUser(BotUser botUser, Map<Bank.BankName, Bank> banksData, int amount) {
        String messageText = buildMessageText(botUser, banksData, amount);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(botUser.getUserChatId());
        sendMessage.setReplyMarkup(new StartCommand().getInlineKeyboardMarkup());
        CurrencyTelegramBot instance = CurrencyTelegramBot.getInstance();
        sendMessage.setText(messageText);
        sendMessage.setParseMode("markdown");
        instance.execute(sendMessage);
    }

    private String buildMessageText(BotUser botUser, Map<Bank.BankName, Bank> banksData, int amount) {
        boolean isAnyBankSelected = false;
        boolean isAnyCurrencySelected = false;
        StringBuilder builder = new StringBuilder();
        for (Bank.BankName bankName : banksData.keySet()) {
            if (botUser.getBanksMap().get(bankName)) {
                isAnyBankSelected = true;
                String bankString = bankName.getMessage() + "\n";
                builder.append(bankString);
                if (banksData.get(bankName).getCurrencyList() != null) {
                    for (Currency currency : banksData.get(bankName).getCurrencyList()) {
                        if (botUser.getCurrenciesMap().get(currency.getCurrencyName())) {
                            isAnyCurrencySelected = true;
                            String currencyString = currency.getCurrencyName().getMessage() + "\n";
                            builder.append(currencyString);
                            int signsAfterComma = botUser.getSignsAfterComma();
                            String buy = "Buy: " + getUserFitAmount(currency.getBuyPrice(), signsAfterComma, amount) + "\n";
                            String sell = "Sell: " + getUserFitAmount(currency.getSellPrice(), signsAfterComma, amount) + "\n";
                            builder.append(buy);
                            builder.append(sell);
                        }
                    }
                } else {
                    builder.append("ERROR: operation refused by bank");
                }
                builder.append("\n");
            }
        }
        String response;
        if(!isAnyBankSelected) {
            response = NO_BANK_SELECTED_MESSAGE;
        } else if(!isAnyCurrencySelected) {
            response = NO_CURRENCY_SELECTED_MESSAGE;
        } else {
            response = String.valueOf(builder);
        }
        return response;
    }

    private String getUserFitAmount(double originValue, int decimalPlaces, int amount) {
        BigDecimal bigDecimal = new BigDecimal(originValue);
        bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(amount));
        bigDecimal = bigDecimal.setScale(decimalPlaces, RoundingMode.HALF_UP);
        DecimalFormat df = new DecimalFormat("0." + "0".repeat(decimalPlaces));
        return df.format(bigDecimal.doubleValue());
    }
}
