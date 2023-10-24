package org.example.MessageProcessingAndSendingPart;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.example.app.Client;
import org.example.bank.Bank;
import org.example.bank.Currency;

import javax.management.timer.Timer;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.example.bank.Bank.BankName.*;
import static org.example.bank.Currency.CurrencyName.EUR;
import static org.example.bank.Currency.CurrencyName.USD;

@Data
public class BotUser {
    private static final int MAX_SIGNS_AFTER_COMMA = 4;
    private static final int DEFAULT_TIME_OF_SENDING = 12;
    private static final int DEFAULT_TIME_ZONE = 0;
    private static final boolean defaultMonobankSetting = false;
    private static final boolean defaultPrivatBankSetting = true;
    private static final boolean defaultNbuSetting = false;
    private static final boolean defaultUsdSetting = true;
    private static final boolean defaultEurSetting = true;

    private long userChatId;
    private int timeOfSending;
    private int timeZone;
    private int signsAfterComma;
    private Map<Bank.BankName, Boolean> banksMap;
    private Map<Currency.CurrencyName, Boolean> currenciesMap;
    @Setter(value = AccessLevel.NONE)
    private ScheduledThreadPoolExecutor executor;
    private BotUser(long userChatId){
        this.userChatId = userChatId;
    }
    public static BotUser newDefaultUserById(long chatId){
        BotUser botUser = new BotUser(chatId);
        botUser.setBanksMap(new HashMap<>());
        botUser.setCurrenciesMap(new HashMap<>());
        botUser.signsAfterComma = MAX_SIGNS_AFTER_COMMA;
        botUser.timeZone = DEFAULT_TIME_ZONE;
        botUser.timeOfSending = DEFAULT_TIME_OF_SENDING;
        botUser.banksMap.put(MONO,defaultMonobankSetting);
        botUser.banksMap.put(PRIVAT, defaultPrivatBankSetting);
        botUser.banksMap.put(NBU, defaultNbuSetting);
        botUser.currenciesMap.put(EUR, defaultEurSetting);
        botUser.currenciesMap.put(USD, defaultUsdSetting);
        return botUser;
    }

    public void setTimeOfSending(int timeOfSending){
        this.timeOfSending = timeOfSending;
        killUserSendingProcess(executor);
        invokeSendingProcess();
    }
    public void setTimeZone(int timeZone){
        this.timeZone = timeZone;
        if(executor.isShutdown()){
            return;
        }else {
            killUserSendingProcess(executor);
            invokeSendingProcess();
        }
    }
    public void setBankNeedByName(Bank.BankName name, Boolean need){
        banksMap.put(name, need);
    }
    public void setCurrencyNeedByName(Currency.CurrencyName name, Boolean need){
        currenciesMap.put(name, need);
    }
    public void killUserSendingProcess(ScheduledThreadPoolExecutor executor){
        if(executor!=null){
            if(!executor.isShutdown()){
                executor.shutdownNow();
            }
        }
    }
    public void invokeSendingProcess(){
        long from = OffsetDateTime.now(ZoneOffset.ofHours(timeZone)).toInstant().toEpochMilli();
        LocalDateTime localTo = LocalDate.now().atTime(timeOfSending, 0, 0);
        long to = localTo.atOffset(ZoneOffset.ofHours(timeZone)).toInstant().toEpochMilli();
        long approximateDiff = to - from;
        if (approximateDiff < 0) {
            approximateDiff = Timer.ONE_DAY + approximateDiff;
        }
        executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(()->{
            BotMessageProcessor processor = new BotMessageProcessor();
            //TODO: add a param with Bank info DTO when it is added
            processor.sendMessageToUser(this, Client.getInstance().getBanks(), 1);
        },approximateDiff, Timer.ONE_DAY, TimeUnit.MILLISECONDS);
    }
}
