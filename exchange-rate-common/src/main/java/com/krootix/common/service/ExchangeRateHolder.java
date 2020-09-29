package com.krootix.common.service;

import com.krootix.common.model.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateHolder<E extends BigDecimal> {

    void update(ExchangeRate<E> rate);

    ExchangeRate<E> getRate();

}