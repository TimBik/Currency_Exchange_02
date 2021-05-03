package ru.itis.jlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.util.Pair;
import ru.itis.jlab.model.Currency;
import ru.itis.jlab.model.EdgeCurrency;
import ru.itis.jlab.repositories.CurrencyRepository;
import ru.itis.jlab.services.matrix.MinLogCostAndPathTablesService;
import ru.itis.jlab.services.modelServices.CurrencyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "ru.itis.jlab")
public class MinCostAndPathMatrixConfig {

    @Autowired
    @Qualifier("bestCurrencyTable")
    private Map<Pair<Currency, Currency>, EdgeCurrency> bestCurrencyTable;


    @Autowired
    CurrencyService currencyService;

    @Autowired
    MinLogCostAndPathTablesService minLogCostAndPathTablesService;

    @Bean(name = "minLogCostAndPathTables")
    public Pair
            <Map<Pair<Currency, Currency>, EdgeCurrency>,
                    Map<Pair<Currency, Currency>, Currency>>
    getMinCostAndPathTablesFloydsAlgorithm() {
        Map<Pair<Currency, Currency>, EdgeCurrency> minCostTable = new HashMap<>();
        Map<Pair<Currency, Currency>, Currency> pathMinCostTable = new HashMap<>();
        for (Pair<Currency, Currency> key : bestCurrencyTable.keySet()) {
            minCostTable.put(key, bestCurrencyTable.get(key));
            pathMinCostTable.put(key, key.getFirst());
        }
        List<Currency> currencyList = currencyService.findAll();
        for (Currency currency : currencyList) {
            minLogCostAndPathTablesService.updateMinCostAndPathTablesWithUsedK(currency, minCostTable, pathMinCostTable);
        }

        return Pair.of(minCostTable, pathMinCostTable);
    }
}
