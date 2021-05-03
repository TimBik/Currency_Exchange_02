package ru.itis.jlab.services.matrix;

import ru.itis.jlab.model.Currency;
import ru.itis.jlab.model.EdgeCurrency;

import java.util.Map;

public interface MatrixService {
    void updateMatrix(EdgeCurrency edgeCurrency);

}
