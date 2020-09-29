package com.krootix.service;

import com.krootix.model.AbstractExchangeRate;
import com.krootix.model.ExchangeRateRuEu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//@Component
//@Configurable
public class ExchangeRateGeneratorImpl implements ExchangeRateGenerator {

    private static final List<AbstractExchangeRate.Names> VALUES =
            List.of(AbstractExchangeRate.Names.values());

    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static AbstractExchangeRate.Names randomName() {
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
//    @Autowired
    public ExchangeRateRuEu generate() {
        logger.info("generating rate");
//        Random random = new Random();
        double random = ThreadLocalRandom.current().nextDouble(min, max);
        double roundOff = Math.round(random * 100.0) / 100.0;
//        double roundOff = Math.round((max - min) * 100.0) / 100.0;

//        double random = ThreadLocalRandom.current().nextDouble(min, max);
        BigDecimal value = BigDecimal.valueOf(roundOff);

        logger.info("rate has been generated with value: {}", value);

        return new ExchangeRateRuEu(randomName().name(), value);
    }

}