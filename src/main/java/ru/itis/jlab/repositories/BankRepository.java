package ru.itis.jlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jlab.model.Bank;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByName(String name);

}
