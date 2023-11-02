package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Dish;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Интерфейс репозитория для работы с блюдами.
 */
public interface DishRepository extends JpaRepository<Dish, Integer> {
    /**
     * Найти все блюда по ID категории с пагинацией.
     *
     * @param categoryId ID категории
     * @param pageable   объект с информацией о странице и сортировке
     * @return страницу с блюдами
     */
    Page<Dish> findByCategoryId(int categoryId, Pageable pageable);

    /**
     * Найти блюдо по ID.
     *
     * @param id ID блюда
     * @return Optional с блюдом или пустой Optional, если блюдо не найдено
     */
    @Nonnull
    Optional<Dish> findById(@Nonnull int id);

    /**
     * Удалить блюдо по ID.
     *
     * @param id ID блюда
     */
    void deleteById(@Nonnull int id);

    /**
     * Найти блюдо по английскому названию.
     *
     * @param nameEn английское название блюда
     * @return Optional с блюдом или пустой Optional, если блюдо не найдено
     */
    Optional<Dish> findByNameEn(@NonNull String nameEn);
}
