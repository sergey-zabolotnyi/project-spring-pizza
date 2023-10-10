package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByLogin(String login);

    @Nonnull
    Optional<Login> findById(@Nonnull Long id);

}
