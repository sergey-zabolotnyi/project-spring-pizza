package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByLoginId(Long id);

    List<Cart> findCartByDish_Id(Long id);

    void deleteByLoginId(Long id);

}
