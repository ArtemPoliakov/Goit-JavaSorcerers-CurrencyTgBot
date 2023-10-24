package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NbuCurrencyDTO {
    @JsonProperty(value = "StartDate")
    private String startDate;
    @JsonProperty(value = "TimeSign")
    private String timeSign;
    @JsonProperty(value = "CurrencyCode")
    private Integer currencyCode;
    @JsonProperty(value = "CurrencyCodeL")
    private String currencyCodeL;
    @JsonProperty(value = "Units")
    private Integer units;
    @JsonProperty(value = "Amount")
    private Double amount;
}
