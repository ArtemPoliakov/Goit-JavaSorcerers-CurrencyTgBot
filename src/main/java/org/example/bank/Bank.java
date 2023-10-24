package org.example.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Bank {
    private List<Currency> currencyList;

    @Getter
    @AllArgsConstructor
    public enum BankName {
        MONO("Monobank rates:"),
        PRIVAT("Privatbank rates:"),
        NBU("NBU rates:"),
        UNDEFINED_BANK("Unexpected bank");

        private final String message;
    }
}
