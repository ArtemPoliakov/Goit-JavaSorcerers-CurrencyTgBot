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
        MONO("_Monobank rates:_"),
        PRIVAT("_Privatbank rates:_"),
        NBU("_NBU rates:_"),
        UNDEFINED_BANK("*Unexpected bank*");

        private final String message;
    }
}
