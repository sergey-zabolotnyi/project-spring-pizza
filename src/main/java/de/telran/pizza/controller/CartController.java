package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.domain.dto.ItemDTO;
import de.telran.pizza.service.CartService;
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

/**
 * Controller class for managing the customer cart.
 */
@Slf4j
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Контроллер корзины пользователей",
        description = "Контроллер для различных операций над корзиной пользователей")
public class CartController {
    private CartService cartService;
    private MessageHelper helper;

    public CartController(CartService cartService, MessageHelper helper) {
        this.cartService = cartService;
        this.helper = helper;
    }

    /**
     * Gets all dishes in the cart associated with the logged-in customer.
     *
     * @return ResponseEntity with the CartDTO containing the list of dishes and their total price.
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
     * Creates a new item in the cart for the logged-in customer.
     *
     * @param itemDTO The ItemDTO containing the details of the item to be added.
     * @return ResponseEntity with the saved Cart entity.
     * @throws ResponseStatusException if there is an issue with the request.
     */
    @PostMapping("/create")
    @Operation(
            summary = "Добавление блюд в корзину пользователей",
            description = "Добавление различных блюд в корзину для вошедшего в систему клиента.")
    public ResponseEntity<Cart> create(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Найменование блюд") ItemDTO itemDTO) {
        log.info(helper.getLogMessage("create.cart.log") + itemDTO.getItemId());
        try {
            return ResponseEntity.ok(cartService.saveNewItem(itemDTO));
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Deletes an item from the cart.
     *
     * @param itemDTO The ItemDTO containing the ID of the item to be deleted.
     * @throws ResponseStatusException if there is an issue with the request.
     */
    @DeleteMapping("/delete")
    @Operation(
            summary = "Удаляет блюда из корзины пользователей",
            description = "Удаляет блюда из корзины для залогиненного пользователя")
    public void delete(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Найменование блюд") ItemDTO itemDTO) {
        log.info(helper.getLogMessage("delete.cart.log") + itemDTO.getItemId());
        try {
            cartService.delete(itemDTO.getItemId());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Deletes all items in the cart associated with the logged-in user.
     *
     * @throws ResponseStatusException if there is an issue with the request.
     */
    @DeleteMapping("/deleteAll")
    @Operation(
            summary = "Удаляет все блюда из корзины пользователей",
            description = "Удаляет все блюда из корзины для залогиненного пользователя")
    public void deleteAll() {
        Long userId = Utils.getAuthorizedLogin().getId();
        log.info(helper.getLogMessage("delete.all.cart.log") + userId);
        try {
            cartService.deleteByLogin(userId);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "delete.all.cart.not");
        }
    }
}

