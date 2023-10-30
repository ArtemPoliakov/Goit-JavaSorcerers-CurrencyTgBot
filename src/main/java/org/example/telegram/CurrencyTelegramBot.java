package org.example.telegram;

import lombok.SneakyThrows;
import org.example.telegram.command.BackCommand;
import org.example.telegram.command.bankTgLogics.BankSelectionCommand;
import org.example.telegram.command.bankTgLogics.BanksCommand;
import org.example.telegram.command.currancyCommandLogic.CurrencyCommand;
import org.example.telegram.command.currancyCommandLogic.CurrencySelectionCommand;
import org.example.telegram.command.signs.DecimalPlaces;
import org.example.telegram.command.signs.SignsAfterComaCommand;
import org.example.telegram.command.startSetupCommands.InfoButtonCommand;
import org.example.telegram.command.startSetupCommands.SettingsCommand;
import org.example.telegram.command.textCommands.HelpCommand;
import org.example.telegram.command.textCommands.StartCommand;
import org.example.telegram.command.timeAndZone.TimeAndZoneCommand;
import org.example.telegram.command.timeAndZone.time.AlertTimesCommand;
import org.example.telegram.command.timeAndZone.time.AlertTimesResetCommand;
import org.example.telegram.command.timeAndZone.time.TurnOffCommand;
import org.example.telegram.command.timeAndZone.zone.ZoneCommand;
import org.example.telegram.command.timeAndZone.zone.ZoneResetCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Pattern;

import static org.example.BotConstants.BOT_NAME;
import static org.example.BotConstants.BOT_TOKEN;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private static CurrencyTelegramBot instance;
    private final Pattern commandPattern = Pattern.compile("/\\w+");

    public static CurrencyTelegramBot getInstance() {
        if (instance == null) {
            instance = new CurrencyTelegramBot();
        }
        return instance;
    }

    @SneakyThrows
    private CurrencyTelegramBot() {
        register(new HelpCommand());
        register(new AlertTimesCommand());
        register(new AlertTimesResetCommand());
        register(new BackCommand());
        register(new BanksCommand());
        register(new CurrencyCommand());
        register(new DecimalPlaces());
        register(new SettingsCommand());
        register(new StartCommand());
        register(new InfoButtonCommand());
        register(new SignsAfterComaCommand());
        register(new TimeAndZoneCommand());
        register(new ZoneCommand());
        register(new ZoneResetCommand());
        register(new BankSelectionCommand());
        register(new TurnOffCommand());
        register(new CurrencySelectionCommand());
        registerDefaultAction(CurrencyTelegramBot::incorrectUserInput);
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
            String[] data = action.split("_");
            if ("settings".equals(data[0])) {
                SettingsCommand settingsCommand = new SettingsCommand(update);
                settingsCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("back".equals(data[0])) {
                BackCommand backCommand = new BackCommand(data[1], update);
                backCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("signsAfterComma".equals(data[0])) {
                SignsAfterComaCommand signsAfterComaCommand = new SignsAfterComaCommand(data[1], update);
                signsAfterComaCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("bank".equals(data[0])) {
                BanksCommand banksCommand = new BanksCommand(update);
                banksCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if("info".equals(data[0])){
                InfoButtonCommand infoButtonCommand = new InfoButtonCommand();
                infoButtonCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("decimalPlaces".equals(data[0])){
                DecimalPlaces decimalPlacesCommand = new DecimalPlaces(update);
                decimalPlacesCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("alertTimes".equals(data[0])) {
                AlertTimesCommand alertTimesCommand = new AlertTimesCommand(update);
                alertTimesCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if("timeAndZone".equals(data[0])){
                TimeAndZoneCommand timeAndZoneCommand = new TimeAndZoneCommand(update);
                timeAndZoneCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if("zone".equals(data[0])){
                ZoneCommand zoneCommand = new ZoneCommand(update);
                zoneCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("alertTimesCommand".equals(data[0])) {
                AlertTimesResetCommand alertTimesResetCommand = new AlertTimesResetCommand(data[1], update);
                alertTimesResetCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            }else if("zone".equals(data[0])){
                ZoneCommand zoneCommand = new ZoneCommand(update);
                zoneCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("zoneResetCommand".equals(data[0])) {
                ZoneResetCommand zoneResetCommand = new ZoneResetCommand(data[1], update);
                zoneResetCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("bank".equals(data[0])) {
                BanksCommand banksCommand = new BanksCommand();
                banksCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("bankSelection".equals(data[0])) {
                BankSelectionCommand bankSelectionCommand = new BankSelectionCommand(data[1], update);
                bankSelectionCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("turnOff".equals(data[0])) {
                TurnOffCommand turnOffCommand = new TurnOffCommand(update);
                turnOffCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("currency".equals(data[0])) {
                CurrencyCommand currencyCommand = new CurrencyCommand(update);
                currencyCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
            } else if ("currencySelection".equals(data[0])) {
                CurrencySelectionCommand currencySelectionCommand = new CurrencySelectionCommand(data[1], update);
                currencySelectionCommand.execute(this, callbackQuery.getFrom(), callbackQuery.getMessage().getChat(), null);
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

    @SneakyThrows
    private static void incorrectUserInput(AbsSender absSender, Message message) {
        SendMessage responseMessage = new SendMessage();
        responseMessage.setText("Ви ввели команду який бот не може розпізнати🤷🏼‍♂️\n" + "Це бот знає ось такі команди: \n" + "/start ~ /help");
        responseMessage.setChatId(message.getChatId());
        absSender.execute(responseMessage);
    }
}