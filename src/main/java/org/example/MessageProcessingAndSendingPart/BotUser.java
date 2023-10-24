package org.example.MessageProcessingAndSendingPart;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.management.timer.Timer;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Data
public class BotUser {
    private static final int MAX_SIGNS_AFTER_COMMA = 4;
    private static final int DEFAULT_TIME_OF_SENDING = 12;
    private static final int DEFAULT_TIME_ZONE = 0;
    private static final boolean defaultMonobankSetting = false;
    private static final boolean defaultPrivatBankSetting = true;
    private static final boolean defaultNbuSetting = false;
    private static final boolean usdSetting = true;
    private static final boolean eurSetting = false;

    private long userChatId;
    private int timeOfSending = DEFAULT_TIME_OF_SENDING;
    private int timeZone = DEFAULT_TIME_ZONE;
    private int signsAfterComma = MAX_SIGNS_AFTER_COMMA;

//    private Map<BankNames, BotUser> banksMap;
//    private Map<CurrencyNames, BotUser> currenciesMap;

//    private boolean isMonoBankNeeded;
//    private boolean isPrivatBankNeeded;
//    private boolean isNbuNeeded;
//    private boolean isUsdNeeded;
//    private boolean isEurNeeded;

    @Setter(value = AccessLevel.NONE)
    private ScheduledThreadPoolExecutor executor;
    private BotUser(long userChatId){
        this.userChatId = userChatId;
        //TODO: do maps setting here when Egor's part arrives
    }
    public BotUser newDefaultUserById(long chatId){
        return new BotUser(chatId);
    }

    public void setTimeOfSending(int timeOfSending){
        this.timeOfSending = timeOfSending;
        if(executor!=null){
            if(!executor.isShutdown()){
                executor.shutdownNow();
            }
        }
        invokeSendingProcess();
    }
    public void setTimeZone(int timeZone){
        this.timeZone = timeZone;
        if(executor!=null){
            if(!executor.isShutdown()){
                executor.shutdownNow();
            }
        }
        invokeSendingProcess();
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
            processor.sendMessageToUser(this);
        },approximateDiff, Timer.ONE_DAY, TimeUnit.MILLISECONDS);
    }
}
