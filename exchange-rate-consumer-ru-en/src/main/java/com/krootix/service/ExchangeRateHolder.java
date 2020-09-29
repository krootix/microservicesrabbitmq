package com.krootix.service;

import com.krootix.model.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateHolder {

    void update(ExchangeRate<BigDecimal> rate);

    ExchangeRate<BigDecimal> getRate();

}