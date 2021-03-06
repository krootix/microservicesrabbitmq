package com.krootix.common.model;

import java.math.BigDecimal;

public interface ExchangeRate<E extends BigDecimal> {

    void setRate(E rate);

    E getRate();

    String getName();
}