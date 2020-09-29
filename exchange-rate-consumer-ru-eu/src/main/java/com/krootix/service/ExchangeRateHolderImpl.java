package com.krootix.service;

import com.krootix.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ExchangeRateHolderImpl implements ExchangeRateHolder {

    private ExchangeRate<BigDecimal> rate;

    @Autowired
    public ExchangeRateHolderImpl(ExchangeRate<BigDecimal> rate) {
        this.rate = rate;
    }

    @Override
    public void update(ExchangeRate<BigDecimal> rate) {
        this.rate = rate;
    }

    @Override
    public ExchangeRate<BigDecimal> getRate() {
        return rate;
    }
}