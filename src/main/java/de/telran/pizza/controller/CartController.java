package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.service.CartService;
import de.telran.pizza.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * Класс контроллера для управления корзиной клиента.
 */
@Slf4j
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Контроллер корзины пользователей",
        description = "Контроллер для различных операций над корзиной пользователей")
public class   CartController {
    private CartService cartService;
    private MessageHelper helper;

    public CartController(CartService cartService, MessageHelper helper) {
        this.cartService = cartService;
        this.helper = helper;
    }

    /**
     * Получает все блюда в корзине, связанные с текущим вошедшим пользователем.
     *
     * @return ResponseEntity с объектом CartDTO, содержащим список блюд и их общую стоимость.
     */
    @GetMapping("/get")
    @Operation(
            summary = "Получение всех блюд в корзине пользователей",
            description = "Получение всех блюд в корзине залогиненного пользователей, хранящихся в БД")
    public ResponseEntity<CartDTO> getDishes() {
        log.info(helper.getMessage("select.all.cart.log") +
                Utils.getAuthorizedLogin().getLogin());
        return ResponseEntity.ok(cartService.findAllDishes());
    }

    /**
     * Создает новый элемент в корзине для вошедшего в систему пользователя.
     *
     * @param id идентификатор добавляемого элемента.
     * @return ResponseEntity с сохраненной сущностью Cart.
     * @throws ResponseStatusException в случае проблем с запросом.
     */
    @PostMapping("/create")
    @Operation(
            summary = "Добавление блюд в корзину пользователей",
            description = "Добавление различных блюд в корзину для вошедшего в систему клиента.")
    public ResponseEntity<Cart> create(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Идентификатор блюда") int id) {
        log.info(helper.getLogMessage("create.cart.log") + id);
        try {
            return ResponseEntity.ok(cartService.saveNewItem(id));
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Удаляет элемент из корзины.
     *
     * @param id идентификатор удаляемого элемента.
     * @throws ResponseStatusException в случае проблем с запросом.
     */
    @DeleteMapping("/delete")
    @Operation(
            summary = "Удаляет блюда из корзины пользователей",
            description = "Удаляет блюда из корзины для залогиненного пользователя")
    public void delete(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Идентификатор блюда") int id) {
        log.info(helper.getLogMessage("delete.cart.log") + id);
        try {
            cartService.delete(id);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Удаляет все элементы в корзине, связанные с текущим вошедшим пользователем.
     *
     * @throws ResponseStatusException в случае проблем с запросом.
     */
    @DeleteMapping("/deleteAll")
    @Operation(
            summary = "Удаляет все блюда из корзины пользователей",
            description = "Удаляет все блюда из корзины для залогиненного пользователя")
    public void deleteAll() {
        int userId = Utils.getAuthorizedLogin().getId();
        log.info(helper.getLogMessage("delete.all.cart.log") + userId);
        try {
            cartService.deleteByLogin(userId);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "delete.all.cart.not");
        }
    }
}

