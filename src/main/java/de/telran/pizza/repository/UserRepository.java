package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Интерфейс репозитория для работы с пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Найти пользователя по логину.
     *
     * @param login логин пользователя
     * @return Optional с пользователем или пустой Optional, если пользователь не найден
     */
    Optional<User> findByUser(String login);

    /**
     * Найти пользователя по ID.
     *
     * @param id ID пользователя
     * @return Optional с пользователем или пустой Optional, если пользователь не найден
     */
    @Nonnull
    Optional<User> findById(@Nonnull int id);
}
