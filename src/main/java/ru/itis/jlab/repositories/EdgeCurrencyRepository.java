package ru.itis.jlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.jlab.model.EdgeCurrency;

import java.util.List;

public interface EdgeCurrencyRepository extends JpaRepository<EdgeCurrency, Long> {
//    Optional<EdgeCurrency> findByBankIdAndCurrencyNames(long bankId, long idFromCurrency, long idToCurrency);

    List<EdgeCurrency> findAllByBankId(long bankId);

    List<EdgeCurrency> findByCurrencyFrom_IdAndCurrencyTo_Id(@Param("id1") Long idCurrencyFrom,
                                                             @Param("id2") Long idCurrencyTo);
}
