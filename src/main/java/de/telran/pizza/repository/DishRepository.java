package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Dish;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    Page<Dish> findByCategoryId(int categoryId, Pageable pageable);

    @Nonnull
    Optional<Dish> findById(@Nonnull int id);

    void deleteById(@Nonnull int id);

    Optional<Dish> findByNameEn(@NonNull String name);
}
