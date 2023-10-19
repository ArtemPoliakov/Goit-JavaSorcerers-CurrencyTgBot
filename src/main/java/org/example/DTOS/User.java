package org.example.DTOS;

import lombok.Data;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Data
public class User {
    private long userChatId;
    private int signsAfterComma;
    private boolean isMonoBankNeeded;
    private boolean isPrivatBankNeeded;
    private boolean isNbuNeeded;
    private boolean isUsdNeeded;
    private boolean isEurNeeded;
    private int timeOfSending;
    private ScheduledThreadPoolExecutor executor;
}
