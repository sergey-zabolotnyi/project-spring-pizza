package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Интерфейс репозитория для работы с отзывами.
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    /**
     * Найти среднюю оценку отзыва.
     *
     * @return средняя оценка отзыва
     */
    @Query(value = "select round(avg(rating), 2) from review", nativeQuery = true)
    Double findAverageReviewRating();
}
