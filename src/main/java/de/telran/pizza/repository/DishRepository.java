package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Dish;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {
    Page<Dish> findByCategoryId(Long categoryId, Pageable pageable);

    @Nonnull
    Optional<Dish> findById(@Nonnull Long id);

    void deleteById(@Nonnull Long id);

    Optional<Dish> findByNameEn(@NonNull String name);
}
