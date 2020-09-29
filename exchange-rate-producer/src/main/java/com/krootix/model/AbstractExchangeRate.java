package com.krootix.model;

import java.math.BigDecimal;

public abstract class AbstractExchangeRate implements ExchangeRate<BigDecimal> {

    protected BigDecimal rate;

    public enum Names {
        RU_EN,
        RU_EU
    }

    public AbstractExchangeRate(BigDecimal rate) {
        this.rate = rate;
    }
}