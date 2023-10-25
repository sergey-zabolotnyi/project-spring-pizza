package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.domain.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findOrdersByLoginId(int id);
    List<Orders> findOrdersByOrderByIdAsc();

    Optional<Orders> findByIdAndLoginIdAndStatus(int id, int loginId, Status status);

    @Modifying
    @Query("UPDATE Orders o SET o.status = :status WHERE o.id = :id")
    void updateStatus(@Param(value = "id") int id,
                      @Param(value = "status") Status status);
    @Query(value = "select sum(total_price) from orders", nativeQuery = true)
    Double findTotalOrdersSum();
    @Query(value = "select round(avg(total_price), 2) from orders", nativeQuery = true)
    Double findAverageOrdersSum();
}
