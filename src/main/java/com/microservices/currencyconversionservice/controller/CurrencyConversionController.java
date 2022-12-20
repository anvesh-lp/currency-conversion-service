package com.microservices.currencyconversionservice.controller;

import com.microservices.currencyconversionservice.models.CurrencyConversion;
import com.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    private final CurrencyExchangeProxy exchangeProxy;

    public CurrencyConversionController(CurrencyExchangeProxy exchangeProxy) {
        this.exchangeProxy = exchangeProxy;
    }

    @GetMapping("/currency-exchange/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversion(@PathVariable BigDecimal quantity, @PathVariable String from, @PathVariable String to) {

        HashMap<String, String> variables = new HashMap<>();
        variables.put("from", from);
        variables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8000/currency-exchange/{from}/to/{to}", CurrencyConversion.class, variables);
        CurrencyConversion currencyConversion = responseEntity.getBody();
        if (currencyConversion == null) {
            throw new RuntimeException("Currenct conversion not found for the inputs " + from + " and " + to);
        }

        return new CurrencyConversion(currencyConversion.getEnvironment()
                , currencyConversion.getId()
                , from
                , to
                , currencyConversion.getConversionMultiple()
                , quantity.multiply(currencyConversion.getConversionMultiple())
                , quantity);
    }

    @GetMapping("/currency-exchange-feign/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateConversionFeign(@PathVariable BigDecimal quantity, @PathVariable String from, @PathVariable String to) {


        CurrencyConversion currencyConversion = exchangeProxy.calculateConversion(from, to);
        if (currencyConversion == null) {
            throw new RuntimeException("Currenct conversion not found for the inputs " + from + " and " + to);
        }

        return new CurrencyConversion(currencyConversion.getEnvironment()
                , currencyConversion.getId()
                , from
                , to
                , currencyConversion.getConversionMultiple()
                , quantity.multiply(currencyConversion.getConversionMultiple())
                , quantity);
    }
}
