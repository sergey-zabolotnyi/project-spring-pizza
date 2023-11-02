package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.domain.entity.enums.Status;
import de.telran.pizza.repository.CartRepository;
import de.telran.pizza.repository.OrdersRepository;
import de.telran.pizza.service.mapper.Mappers;import de.telran.pizza.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Класс-сервис для управления заказами.
 * Этот класс используется для управления заказами приложения.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Service
@Slf4j
public class OrderService {
    private OrdersRepository ordersRepository;
    private CartRepository cartRepository;
    private MessageHelper helper;
    private Mappers mappers;

    public OrderService(OrdersRepository ordersRepository, CartRepository cartRepository, Mappers mappers, MessageHelper helper) {
        this.ordersRepository = ordersRepository;
        this.cartRepository = cartRepository;
        this.mappers = mappers;
        this.helper = helper;
    }

    /**
     * Получает список заказов, принадлежащих текущему авторизованному пользователю.
     *
     * @return Список сущностей заказов.
     */
    public List<Orders> findAllUserOrders() {
        return ordersRepository.findOrdersByUserId(Utils.getAuthorizedLogin().getId());
    }

    /**
     * Получает список всех заказов.
     *
     * @return Список всех заказов пользователей.
     */
    public List<Orders> findAllOrders() {
        return ordersRepository.findOrdersByOrderByIdAsc();
    }

    /**
     * Сохраняет новый заказ в базу данных на основе содержимого текущей корзины пользователя.
     *
     * @return Сохраненная сущность заказа.
     * @throws NoSuchElementException если корзина пользователя пуста.
     */
    @Transactional
    public Orders saveNewOrder() throws NoSuchElementException {
        User user = Utils.getAuthorizedLogin();

        List<Cart> newCart = cartRepository.findAllByUserId(user.getId());
        if (newCart.isEmpty()) {
            throw new NoSuchElementException(helper.getLogMessage("select.all.carts.empty"));
        }
        List<DishDTO> dishes = mappers.cartListToDishDTOList(newCart);
        BigDecimal dishTotalPrice = mappers.calculateTotalPrice(dishes);
        List<Dish> orderDishesList = mappers.orderDishesList(dishes);

        cartRepository.deleteByUserId(user.getId());
        log.info(helper.getLogMessage("delete.all.cart.log"));

        return ordersRepository.save(Orders.builder()
                .user(user)
                .totalPrice(dishTotalPrice)
                .status(Status.NEW)
                .time(LocalDateTime.now())
                .dishes(orderDishesList)
                .build());
    }

    /**
     * Подтверждает заказ с указанным ID.
     *
     * @param id ID заказа для подтверждения.
     * @return true, если подтверждение успешно, в противном случае - false.
     * @throws NoSuchElementException если заказ не найден или у пользователя нет разрешения.
     */
    @Transactional
    public boolean confirm(int id) {
        Orders order = ordersRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(helper.getLogMessage("select.orders.not") + id));
        if (!Utils.getAuthorizedLogin().getRole().equals(Role.ROLE_MANAGER) ||
                order.getStatus().equals(Status.DONE) || order.getStatus().equals(Status.NEW)) {
            throw new NoSuchElementException(helper.getLogMessage("select.orders.not") + id);
        }
        ordersRepository.updateStatus(id, order.getStatus().next());
        return true;
    }

    /**
     * Обрабатывает оплату заказа с указанным ID.
     *
     * @param id ID заказа для оплаты.
     * @return true, если оплата успешна, в противном случае - false.
     * @throws NoSuchElementException если заказ не найден или у пользователя нет разрешения.
     */
    @Transactional
    public boolean payment(int id) {
        int userId = Utils.getAuthorizedLogin().getId();

        ordersRepository.findByIdAndUserIdAndStatus(id, userId, Status.NEW)
                .orElseThrow(() -> new NoSuchElementException(
                        helper.getLogMessage("select.orders.not") + id));

        ordersRepository.updateStatus(id, Status.PAYED);
        return true;
    }

    /**
     * Получает среднюю сумму заказов.
     *
     * Этот метод вычисляет и возвращает среднюю сумму всех заказов в репозитории.
     *
     * @return Объект типа Double, представляющий среднюю сумму заказов.
     */
    public Double getAverageOrdersSum() {
        return ordersRepository.findAverageOrdersSum();
    }

    /**
     * Получает общую сумму заказов.
     *
     * Этот метод вычисляет и возвращает общую сумму всех заказов в репозитории.
     *
     * @return Объект типа Double, представляющий общую сумму заказов.
     */
    public Double getTotalOrdersSum() {
        return ordersRepository.findTotalOrdersSum();
    }

    /**
     * Возвращает список блюд для заказа с указанным ID.
     *
     * @param orderId ID заказа.
     * @return Список DTO объектов блюд.
     */
    public List<DishDTO> getDishesByOrderId(int orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException(helper.getLogMessage("select.orders.not") + orderId));

        return order.getDishes().stream()
                .map(mappers::dishToDishDTO)
                .collect(Collectors.toList());
    }
}