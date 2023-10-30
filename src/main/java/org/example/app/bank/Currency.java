package org.example.app.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Currency {
    private final CurrencyName currencyName;
    private final double buyPrice;
    private final double sellPrice;

    @Getter
    @AllArgsConstructor
    public enum CurrencyName {
        USD("*USD/UAH*:"),
        EUR("*EUR/UAH*:"),
        UNDEFINED("*Unexpected currency*");

        private final String message;
    }
}
