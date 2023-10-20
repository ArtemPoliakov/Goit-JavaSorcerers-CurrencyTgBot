package org.example.MessageProcessingAndSendingPart;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.management.timer.Timer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Data
public class BotUser {
    private long userChatId;
    private int signsAfterComma;
    private boolean isMonoBankNeeded;
    private boolean isPrivatBankNeeded;
    private boolean isNbuNeeded;
    private boolean isUsdNeeded;
    private boolean isEurNeeded;
    private int timeOfSending;
    private int timeZone;

    @Setter(value = AccessLevel.NONE)
    private ScheduledThreadPoolExecutor executor;

    public void setTimeOfSending(int timeOfSending){
        this.timeOfSending = timeOfSending;
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
