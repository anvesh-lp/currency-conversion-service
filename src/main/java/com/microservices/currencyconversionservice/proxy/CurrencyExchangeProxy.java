package com.microservices.currencyconversionservice.proxy;

import com.microservices.currencyconversionservice.models.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "Currency-exchange-service", url = "localhost:8000")
@FeignClient(name = "Currency-exchange-service")
public interface CurrencyExchangeProxy {


    @GetMapping("/currency-exchange/{from}/to/{to}")
    public CurrencyConversion calculateConversion(@PathVariable String from, @PathVariable String to);

}
