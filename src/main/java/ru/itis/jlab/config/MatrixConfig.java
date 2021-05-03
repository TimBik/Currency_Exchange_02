package ru.itis.jlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.util.Pair;
import ru.itis.jlab.model.Bank;
import ru.itis.jlab.model.Currency;
import ru.itis.jlab.model.EdgeCurrency;
import ru.itis.jlab.services.matrix.BestEdgeCurrenciesService;
import ru.itis.jlab.services.modelServices.CurrencyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@ComponentScan(basePackages = "ru.itis.jlab")
public class MatrixConfig {


    @Autowired
    CurrencyService currencyService;

    @Autowired
    BestEdgeCurrenciesService bestEdgeCurrenciesService;

    @Bean(name = "bestCurrencyTable")
    public Map<Pair<Currency, Currency>, EdgeCurrency> getBestCurrencyTable() {
        Map<Pair<Currency, Currency>, EdgeCurrency> matrix = new HashMap<>();
        //TO DO: оптимизировать, но долго
        // работает только при запуске сервера, если большая бд
        List<Currency> currencyList = currencyService.findAll();
        for (Currency currency1 : currencyList) {
            for (Currency currency2 : currencyList) {
                if (currency1.equals(currency2)) {

                    matrix.put(Pair.of(currency1, currency2), createSimpleEdgeCurrency(currency1, currency2));
                } else {
                    Optional<EdgeCurrency> edgeCurrency = bestEdgeCurrenciesService.createBestEdgeCurrencyByCurrencies(currency1, currency2);
                    if (edgeCurrency.isPresent()) {
                        matrix.put(Pair.of(currency1, currency2), edgeCurrency.get());
                    } else {
                        matrix.put(Pair.of(currency1, currency2), fixEdgeCurrency(currency1, currency2));
                    }
                }
            }
        }
        return matrix;
    }


    private EdgeCurrency fixEdgeCurrency(Currency currency1, Currency currency2) {
        return EdgeCurrency.builder()
                .currencyFrom(currency1)
                .currencyTo(currency2)
                .logCostByOne(Double.MIN_VALUE)
                .costByOne(-1.)
                .build();
    }

    private EdgeCurrency createSimpleEdgeCurrency(Currency currency1, Currency currency2) {
        return EdgeCurrency.builder()
                .currencyFrom(currency1)
                .currencyTo(currency2)
                .logCostByOne(0.)
                .costByOne(1.)
                .bank(Bank.builder().name("any bank").build())
                .build();
    }

}
