package ru.itis.jlab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jlab.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByConfirmCode(String code);

    Optional<User> findByLogin(String login);

    Optional<User> findByMail(String email);

    void deleteById(Long id);
}
