package com.krootix.model;

import java.math.BigDecimal;

public class ExchangeRateRuEn extends AbstractExchangeRate {

    private String name;

    public ExchangeRateRuEn(){}

    public ExchangeRateRuEn(String name) {
        super();
        this.name = name;
    }

    public ExchangeRateRuEn(String name, BigDecimal rate) {
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