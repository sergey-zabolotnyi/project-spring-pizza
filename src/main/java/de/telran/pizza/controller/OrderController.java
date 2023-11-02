package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Orders;
import de.telran.pizza.service.OrderService;
import de.telran.pizza.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для управления заказами.
 */
@Slf4j
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Контроллер заказов клиентов", description = "Контроллер заказов клиентов пиццерии")
public class OrderController {
    private OrderService orderService;
    private MessageHelper helper;

    public OrderController(OrderService orderService, MessageHelper helper) {
        this.orderService = orderService;
        this.helper = helper;
    }

    /**
     * Получает список заказов, принадлежащих текущему авторизованному пользователю.
     *
     * @return ResponseEntity, содержащий список сущностей Orders.
     */
    @GetMapping("/get")
    @Operation(summary = "Получаем список заказов",
            description = "Получаем список заказов, авторизованного пользователя пиццерии.")
    public ResponseEntity<List<Orders>> getOrders() {
        log.info(helper.getLogMessage("select.all.orders.log") + Utils.getAuthorizedLogin().getUser());
        return ResponseEntity.ok(orderService.findAllUserOrders());
    }

    /**
     * Получает список всех заказов для менеджера.
     *
     * @return ResponseEntity, содержащий список сущностей Orders.
     */
    @GetMapping("/getAll")
    @Operation(summary = "Получаем все списки заказов",
            description = "Получаем все списки заказов для менеджера.")
    public ResponseEntity<List<Orders>> getAllOrders() {
        log.info(helper.getLogMessage("select.all.orders.manager.log") + Utils.getAuthorizedLogin().getUser());
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    /**
     * Создает новый заказ для текущего авторизованного пользователя.
     *
     * @return ResponseEntity, содержащий созданную сущность Orders.
     */
    @PostMapping("/create")
    @Operation(summary = "Создание нового заказа",
            description = "Создание нового заказа для авторизованного пользователя пиззерии.")
    public ResponseEntity<Orders> create() {
        log.info(helper.getLogMessage("create.order.log") + Utils.getAuthorizedLogin().getUser());
        try {
            return ResponseEntity.ok(orderService.saveNewOrder());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Подтверждает заказ с указанным идентификатором.
     *
     * @param id идентификатор заказа для подтверждения.
     */
    @PutMapping("/confirm")
    @Operation(summary = "Подтверждает заказ",
            description = "Подтверждает заказ с указанным идентификатором.")
    public void confirm(@Valid @RequestBody int id) {
        log.info(helper.getLogMessage("update.orders.log") + id);
        try {
            orderService.confirm(id);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Обрабатывает оплату заказа с указанным идентификатором.
     *
     * @param id идентификатор заказа для оплаты.
     */
    @PutMapping("/payment")
    @Operation(summary = "Обрабатывает оплату заказа",
            description = "Обрабатывает оплату заказа с указанным идентификатором.")
    public void payment(@Valid @RequestBody int id) {
        log.info(helper.getLogMessage("update.orders.log") + id);
        try {
            orderService.payment(id);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Получение количества всех заказов.
     *
     * Этот метод позволяет получить общее количество заказов в пиццерии.
     *
     * @return ResponseEntity с общим количеством заказов.
     */
    @GetMapping("/count")
    @Operation(summary = "Получаем количество всех заказов",
            description = "Получаем количество всех заказов, пиццерии.")
    public ResponseEntity<Integer> getOrdersCount() {
        log.info(helper.getLogMessage("get.orders.count"));
        return ResponseEntity.ok(orderService.findAllOrders().size());
    }

    /**
     * Получение средней суммы всех заказов.
     *
     * Этот метод позволяет получить среднюю сумму всех заказов в пиццерии.
     *
     * @return ResponseEntity с средней суммой всех заказов.
     */
    @GetMapping("/average")
    @Operation(summary = "Получаем среднюю сумму всех заказов",
            description = "Получаем среднюю сумму всех заказов, пиццерии.")
    public ResponseEntity<Double> getAverageSum() {
        log.info(helper.getLogMessage("get.average.order.sum"));
        return ResponseEntity.ok(orderService.getAverageOrdersSum());
    }

    /**
     * Получает общую сумму всех заказов.
     *
     * Этот метод позволяет пользователям пиццерии получить общую сумму всех сделанных заказов.
     *
     * @return ResponseEntity с общей суммой всех заказов.
     */
    @GetMapping("/get_total_sum")
    @Operation(summary = "Получаем сумму всех заказов",
            description = "Получаем сумму всех заказов, пользователями пиццерии.")
    public ResponseEntity<Double> getAllOrdersAmount() {
        log.info(helper.getLogMessage("get.all.orders.sum"));
        return ResponseEntity.ok(orderService.getTotalOrdersSum());
    }

    /**
     * Получает список блюд по номеру заказа.
     *
     * Этот метод позволяет пользователям получить список заказанных блюд по номеру заказа.
     *
     * @return ResponseEntity со списком блюд данного заказа.
     */
    @GetMapping("/get_dishes/{orderId}")
    @Operation(summary = "Получаем список блюд по номеру заказа",
            description = "Получаем список всех блюд для заказа с указанным ID.")
    public ResponseEntity<List<DishDTO>> getDishesByOrderId(@PathVariable int orderId) {
        List<DishDTO> dishes = orderService.getDishesByOrderId(orderId);
        return ResponseEntity.ok(dishes);
    }
}

