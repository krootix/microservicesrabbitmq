package com.krootix.service;

import com.krootix.model.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateGenerator {

    ExchangeRate<BigDecimal> generate();

}