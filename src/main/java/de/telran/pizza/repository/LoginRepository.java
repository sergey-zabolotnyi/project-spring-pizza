package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
    Optional<Login> findByLogin(String login);

    @Nonnull
    Optional<Login> findById(@Nonnull int id);

}
