package com.krootix.model;

import java.math.BigDecimal;

public class ExchangeRateRu extends AbstractExchangeRate {

    private final String name;

    public ExchangeRateRu(String name, BigDecimal rate) {
        super(rate);
        this.name = name;
    }

    @Override
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String getName() {
        return name;
    }
}