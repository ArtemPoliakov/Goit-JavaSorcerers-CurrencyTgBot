package org.example.app.bank;

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
        MONO("_Монобанк:_"),
        PRIVAT("_Приватбанк:_"),
        NBU("_НБУ:_"),
        UNDEFINED_BANK("*Unexpected bank*");

        private final String message;
    }
}
