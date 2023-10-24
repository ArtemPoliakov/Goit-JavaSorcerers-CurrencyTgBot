package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivatBankCurrencyDTO {
    private String ccy;
    private String base_ccy;
    private Double buy;
    private Double sale;
}
