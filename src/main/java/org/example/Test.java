package org.example;

import org.example.app.Client;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Client client = Client.getInstance();
        Thread.sleep(2000);
        client.getBanks().values().forEach(System.out::println);
    }
}
