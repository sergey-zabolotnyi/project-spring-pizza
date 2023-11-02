package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.domain.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для работы с заказами.
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    /**
     * Найти все заказы по ID пользователя.
     *
     * @param id ID пользователя
     * @return список заказов
     */
    List<Orders> findOrdersByUserId(int id);

    /**
     * Найти все заказы и отсортировать их по ID по возрастанию.
     *
     * @return список заказов, отсортированных по ID
     */
    List<Orders> findOrdersByOrderByIdAsc();

    /**
     * Найти заказ по ID, ID пользователя и статусу.
     *
     * @param id       ID заказа
     * @param loginId  ID пользователя
     * @param status   статус заказа
     * @return Optional с заказом или пустой Optional, если заказ не найден
     */
    Optional<Orders> findByIdAndUserIdAndStatus(int id, int loginId, Status status);

    /**
     * Обновить статус заказа по ID.
     *
     * @param id     ID заказа
     * @param status новый статус заказа
     */
    @Modifying
    @Query("UPDATE Orders o SET o.status = :status WHERE o.id = :id")
    void updateStatus(@Param(value = "id") int id,
                      @Param(value = "status") Status status);

    /**
     * Найти сумму всех заказов.
     *
     * @return сумма всех заказов
     */
    @Query(value = "select sum(total_price) from orders", nativeQuery = true)
    Double findTotalOrdersSum();

    /**
     * Найти среднюю сумму заказов.
     *
     * @return средняя сумма заказов
     */
    @Query(value = "select round(avg(total_price), 2) from orders", nativeQuery = true)
    Double findAverageOrdersSum();
}