package ru.itis.jlab.services.matrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.itis.jlab.dto.EdgeCurrencyWithNamesDto;
import ru.itis.jlab.model.Currency;
import ru.itis.jlab.model.EdgeCurrency;
import ru.itis.jlab.services.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PathCurrencyExchangeServiceImpl implements PathCurrencyExchangeService {

    @Autowired
    @Qualifier("minLogCostAndPathTables")
    Pair<Map<Pair<Currency, Currency>, EdgeCurrency>, Map<Pair<Currency, Currency>, Currency>> minLogCostAndPathTables;

    @Autowired
    @Qualifier("bestCurrencyTable")
    Map<Pair<Currency, Currency>, EdgeCurrency> bestCurrencyTable;

    @Autowired
    Converter converter;

    @Override
    public List<EdgeCurrencyWithNamesDto> findBestPath(Currency startCurrency, Currency endCurrency) {
        List<EdgeCurrencyWithNamesDto> ansPath = new ArrayList<>();
        Map<Pair<Currency, Currency>, Currency> allPath = minLogCostAndPathTables.getSecond();
        Currency midCurrency = allPath.get(Pair.of(startCurrency, endCurrency));
        while (!midCurrency.equalsId(startCurrency)) {
            EdgeCurrency edgeCurrency = bestCurrencyTable.get(Pair.of(midCurrency, endCurrency));
            EdgeCurrencyWithNamesDto edgeCurrencyWithNamesDto = converter.convertFromEdgeCurrencyToEdgeCurrencyWithNamesDto(edgeCurrency);
            ansPath.add(edgeCurrencyWithNamesDto);
            endCurrency = midCurrency;
            midCurrency = allPath.get(Pair.of(startCurrency, midCurrency));
        }
        EdgeCurrency edgeCurrency = bestCurrencyTable.get(Pair.of(midCurrency, endCurrency));
        EdgeCurrencyWithNamesDto edgeCurrencyWithNamesDto = converter.convertFromEdgeCurrencyToEdgeCurrencyWithNamesDto(edgeCurrency);
        if (edgeCurrency.getBank() != null && !edgeCurrency.getBank().getName().equals("any bank"))
            ansPath.add(edgeCurrencyWithNamesDto);
        return ansPath;
    }

}
