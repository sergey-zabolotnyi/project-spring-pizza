package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByLoginId(int id);

    List<Cart> findCartByDish_Id(int id);

    void deleteByLoginId(int id);

}
