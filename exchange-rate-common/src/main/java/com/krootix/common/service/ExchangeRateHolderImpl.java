package com.krootix.common.service;

import com.krootix.common.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ExchangeRateHolderImpl implements ExchangeRateHolder<BigDecimal> {

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