package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.enums.Role;
import de.telran.pizza.domain.entity.enums.Status;
import de.telran.pizza.repository.CartRepository;
import de.telran.pizza.repository.OrdersRepository;
import de.telran.pizza.service.mapper.DishMapper;
import de.telran.pizza.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing orders.
 */
@Service
@Slf4j
public class OrderService {
    private OrdersRepository ordersRepository;
    private CartRepository cartRepository;
    private MessageHelper helper;

    public OrderService(OrdersRepository ordersRepository, CartRepository cartRepository, MessageHelper helper) {
        this.ordersRepository = ordersRepository;
        this.cartRepository = cartRepository;
        this.helper = helper;
    }

    /**
     * Gets a list of orders belonging to the currently authorized user.
     *
     * @return A list of Orders entities.
     */
    public List<Orders> findAllUserOrders() {
        return ordersRepository.findOrdersByLoginId(Utils.getAuthorizedLogin().getId());
    }

    /**
     * Gets a list of all orders.
     *
     * @return A list of Orders entities.
     */
    public List<Orders> findAllOrders() {
        return ordersRepository.findOrdersByOrderByIdAsc();
    }

    /**
     * Saves a new order to the database based on the current user's cart contents.
     *
     * @return The saved Orders entity.
     * @throws NoSuchElementException if the user's cart is empty.
     */
    @Transactional
    public Orders saveNewOrder() throws NoSuchElementException {
        Login user = Utils.getAuthorizedLogin();

        List<Cart> newCart = cartRepository.findAllByLoginId(user.getId());
        if (newCart.isEmpty()) {
            throw new NoSuchElementException(helper.getLogMessage("select.all.carts.empty"));
        }
        List<DishDTO> dishes = DishMapper.listDishesDTOtoCart(newCart);
        BigDecimal dishTotalPrice = DishMapper.getDishTotalPrice(dishes);

        cartRepository.deleteByLoginId(user.getId());
        log.info(helper.getLogMessage("delete.all.cart.log"));

        return ordersRepository.save(Orders.builder()
                .login(user)
                .totalPrice(dishTotalPrice)
                .status(Status.NEW)
                .time(LocalDateTime.now())
                .build());
    }

    /**
     * Confirms an order with the specified ID.
     *
     * @param id The ID of the order to be confirmed.
     * @return True if the confirmation is successful, false otherwise.
     * @throws NoSuchElementException if the order is not found or the user lacks authorization.
     */
    @Transactional
    public boolean confirm(Long id) {
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
     * Processes a payment for an order with the specified ID.
     *
     * @param id The ID of the order to be paid.
     * @return True if the payment is successful, false otherwise.
     * @throws NoSuchElementException if the order is not found or the user lacks authorization.
     */
    @Transactional
    public boolean payment(Long id) {
        Long loginId = Utils.getAuthorizedLogin().getId();

        ordersRepository.findByIdAndLoginIdAndStatus(id, loginId, Status.NEW)
                .orElseThrow(() -> new NoSuchElementException(
                        helper.getLogMessage("select.orders.not") + id));

        ordersRepository.updateStatus(id, Status.PAYED);
        return true;
    }

    /**
     * Gets the average sum of orders.
     * <p>
     * This method calculates and returns the average sum of all orders in the repository.
     *
     * @return A {@code Double} representing the average sum of orders.
     */
    public Double getAverageOrdersSum() {
        return ordersRepository.findAverageOrdersSum();
    }

    /**
     * Gets the total sum of orders.
     * <p>
     * This method calculates and returns the total sum of all orders in the repository.
     *
     * @return A {@code Double} representing the total sum of orders.
     */
    public Double getTotalOrdersSum() {
        return ordersRepository.findTotalOrdersSum();
    }
}