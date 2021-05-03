package ru.itis.jlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jlab.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByBankName(String bankName);
}
