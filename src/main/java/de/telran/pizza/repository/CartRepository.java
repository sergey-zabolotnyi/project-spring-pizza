package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Интерфейс репозитория для работы с корзиной.
 */
public interface CartRepository extends JpaRepository<Cart, Integer> {

    /**
     * Находит все элементы корзины по ID пользователя.
     *
     * @param id идентификатор пользователя
     * @return список элементов корзины
     */
    List<Cart> findAllByUserId(int id);

    /**
     * Находит все элементы корзины по ID блюда.
     *
     * @param id идентификатор блюда
     * @return список элементов корзины
     */
    List<Cart> findCartByDish_Id(int id);

    /**
     * Удаляет все элементы корзины по ID пользователя.
     *
     * @param id идентификатор пользователя
     */
    void deleteByUserId(int id);
}
