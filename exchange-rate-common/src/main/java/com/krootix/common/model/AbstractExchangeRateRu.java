package com.krootix.common.model;

import java.math.BigDecimal;

public abstract class AbstractExchangeRateRu implements ExchangeRate<BigDecimal> {

    protected BigDecimal rate;

    public AbstractExchangeRateRu() {

    }

    public enum Names {
        RU_EN,
        RU_EU
    }

    public AbstractExchangeRateRu(BigDecimal rate) {
        this.rate = rate;
    }
}