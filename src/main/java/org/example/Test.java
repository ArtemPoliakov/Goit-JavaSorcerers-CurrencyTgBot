package org.example;

import org.example.MessageProcessingAndSendingPart.BotUser;
import org.example.app.Client;
import org.example.app.Database;
import org.example.bank.Bank;
import org.example.bank.Currency;
import org.example.dto.MonoBankCurrencyDTO;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        Client client = Client.getInstance();
//        Thread.sleep(2000);
//        client.getBanks().values().forEach(System.out::println);

//        Map<Bank.BankName, Bank> map =
//                Map.ofEntries(entry(Bank.BankName.MONO,new Bank()),
//                        entry(Bank.BankName.NBU, new Bank()));
//        System.out.println(map.get(Bank.BankName.MONO));
//        map.get(Bank.BankName.MONO).setCurrencyList(Client.getInstance().getNbuCurrentCurrencyList());
//        Thread.sleep(2000);
//        System.out.println(map.get(Bank.BankName.MONO));

        Database.getDatabase().getUsersMap().put(1L, BotUser.newDefaultUserById(1));
        System.out.println(Database.getDatabase().getUsersMap().get(1L));
    }
}
