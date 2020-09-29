package com.krootix.model;

import java.math.BigDecimal;

public class ExchangeRateRuEu extends AbstractExchangeRate {

    private String name;

    public ExchangeRateRuEu() {
    }

    public ExchangeRateRuEu(String name) {
        super();
        this.name = name;
    }

    public ExchangeRateRuEu(String name, BigDecimal rate) {
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