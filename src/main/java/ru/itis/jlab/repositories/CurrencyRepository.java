package ru.itis.jlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jlab.model.Currency;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByName(String nameCurrency);
}
