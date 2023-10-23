package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonoBankCurrencyDTO {
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private Long date;
    private Double rateBuy;
    private Double rateCross;
    private Double rateSell;
}
