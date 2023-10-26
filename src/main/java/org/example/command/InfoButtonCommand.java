package org.example.command;

import lombok.SneakyThrows;
import org.example.MessageProcessingAndSendingPart.BotMessageProcessor;
import org.example.MessageProcessingAndSendingPart.BotUser;
import org.example.app.Client;
import org.example.app.Database;
import org.example.bank.Bank;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;

public class InfoButtonCommand extends BotCommand {
    public InfoButtonCommand() {
        super("info", "Отримати інфо📚");
    }

    @Override
    @SneakyThrows
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        if (!Database.getDatabase().getUsersMap().containsKey(chat.getId())){
            Database.getDatabase().getUsersMap().put(chat.getId(), BotUser.newDefaultUserById(chat.getId()));
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());

        BotUser botUser = Database.getDatabase().getUsersMap().get(chat.getId());
        Map<Bank.BankName, Bank> banks = Client.getInstance().getBanks();

        BotMessageProcessor botMessageProcessor = new BotMessageProcessor();
        String buildMessageText = botMessageProcessor.buildMessageText(botUser, banks, 1);

        System.out.println("Text buuton getInfo ");
        sendMessage.setChatId(chat.getId());
        sendMessage.setChatId(buildMessageText);
        absSender.execute(sendMessage);
    }
}
