package com.krootix.common.service;

import com.krootix.common.model.AbstractExchangeRateRu;
import com.krootix.common.model.ExchangeRate;
import com.krootix.common.model.ExchangeRateRu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//@Component
//@Configurable
public class ExchangeRateGeneratorImpl implements ExchangeRateGenerator<BigDecimal> {

    private static final List<AbstractExchangeRateRu.Names> VALUES =
            List.of(AbstractExchangeRateRu.Names.values());

    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static AbstractExchangeRateRu.Names randomName() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateGeneratorImpl.class);

    private double min = 65;
    private double max = 80;

    public ExchangeRateGeneratorImpl(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public ExchangeRate generate() {
        logger.info("generating rate");
        double random = ThreadLocalRandom.current().nextDouble(min, max);
        double roundOff = Math.round(random * 100.0) / 100.0;
        BigDecimal value = BigDecimal.valueOf(roundOff);

        logger.info("rate has been generated with value: {}", value);

        return new ExchangeRateRu(randomName().name(), value);
    }
}