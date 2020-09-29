package com.krootix.common.model;

import java.math.BigDecimal;

public class ExchangeRateRu extends AbstractExchangeRateRu {

    private String name;

    public ExchangeRateRu() {
    }

    public ExchangeRateRu(String name) {
        super();
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "ExchangeRateRu{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }
}