package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
