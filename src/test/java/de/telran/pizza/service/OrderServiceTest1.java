package de.telran.pizza.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.*;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.domain.entity.enums.Status;
import de.telran.pizza.repository.CartRepository;
import de.telran.pizza.repository.OrdersRepository;
import de.telran.pizza.security.UserDetailSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

class OrderServiceTest1 {

    private OrdersRepository ordersRepository;
    private OrderService orderService;
    private CartRepository cartRepository;
    private MessageHelper helper;

    @BeforeEach
    void setUp() {
        // Создаем mock для ordersRepository
        ordersRepository = mock(OrdersRepository.class);
        // Создаем mock для cartRepository
        cartRepository = mock(CartRepository.class);
        // Создаем mock для helper
        helper = mock(MessageHelper.class);
        // Создаем объект OrderService с передачей всех необходимых зависимостей
        orderService = new OrderService(ordersRepository, cartRepository, helper);
    }

    
}
