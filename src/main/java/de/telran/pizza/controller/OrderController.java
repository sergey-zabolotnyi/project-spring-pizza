package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.domain.entity.dto.ItemDTO;
import de.telran.pizza.service.OrderService;
import de.telran.pizza.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for managing orders.
 */
@Slf4j
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Контроллер заказов клиентов", description = "Контроллер заказов клиентов пиццерии")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MessageHelper helper;

    /**
     * Gets a list of orders belonging to the currently authorized user.
     *
     * @return A ResponseEntity containing a list of Orders entities.
     */
    @GetMapping("/get")
    @Operation(summary = "Получаем список заказов",
            description = "Получаем список заказов, авторизованного пользователя пиццерии.")
    public ResponseEntity<List<Orders>> getOrders() {
        log.info(helper.getLogMessage("select.all.orders.log") + Utils.getAuthorizedLogin().getLogin());
        return ResponseEntity.ok(orderService.findAllUserOrders());
    }

    /**
     * Gets a list of all orders for a manager.
     *
     * @return A ResponseEntity containing a list of Orders entities.
     */
    @GetMapping("/getAll")
    @Operation(summary = "Получаем все списки заказов",
            description = "Получаем все списки заказов для менеджера.")
    public ResponseEntity<List<Orders>> getAllOrders() {
        log.info(helper.getLogMessage("select.all.orders.manager.log") + Utils.getAuthorizedLogin().getLogin());
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    /**
     * Creates a new order for the currently authorized user.
     *
     * @return A ResponseEntity containing the created Orders entity.
     */
    @PostMapping("/create")
    @Operation(summary = "Создание нового заказа",
            description = "Создание нового заказа для авторизованного пользователя пиззерии.")
    public ResponseEntity<Orders> create() {
        log.info(helper.getLogMessage("create.order.log") + Utils.getAuthorizedLogin().getLogin());
        try {
            return ResponseEntity.ok(orderService.saveNewOrder());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Confirms an order with the specified ID.
     *
     * @param itemDTO The ItemDTO containing the ID of the order to be confirmed.
     */
    @PutMapping("/confirm")
    @Operation(summary = "Подтверждает заказ",
            description = "Подтверждает заказ с указанным идентификатором.")
    public void confirm(@Valid @RequestBody ItemDTO itemDTO) {
        log.info(helper.getLogMessage("update.orders.log") + itemDTO.getItemId());
        try {
            orderService.confirm(itemDTO.getItemId());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Processes a payment for an order with the specified ID.
     *
     * @param itemDTO The ItemDTO containing the ID of the order to be paid.
     */
    @PutMapping("/payment")
    @Operation(summary = "Обрабатывает оплату заказа",
            description = "Обрабатывает оплату заказа с указанным идентификатором.")
    public void payment(@Valid @RequestBody ItemDTO itemDTO) {
        log.info(helper.getLogMessage("update.orders.log") + itemDTO.getItemId());
        try {
            orderService.payment(itemDTO.getItemId());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

