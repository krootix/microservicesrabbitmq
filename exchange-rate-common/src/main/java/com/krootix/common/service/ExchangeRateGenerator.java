package com.krootix.common.service;

import com.krootix.common.model.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateGenerator<E extends BigDecimal> {

    ExchangeRate<E> generate();

}