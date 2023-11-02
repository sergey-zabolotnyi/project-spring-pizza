package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс репозитория для работы с категориями.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
