package de.telran.pizza.repository;

import de.telran.pizza.domain.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {

}
