package com.microservices.currencyconversionservice.models;


import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyConversion {
    private String environment;
    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiple;
    private BigDecimal totalCalculated;
     private BigDecimal quantity;


}
