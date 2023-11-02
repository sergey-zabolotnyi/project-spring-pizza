package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.repository.CartRepository;
import de.telran.pizza.repository.DishRepository;
import de.telran.pizza.service.mapper.Mappers;
import de.telran.pizza.utils.Utils;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервисный класс для управления корзиной покупок.
 * Этот класс используется для управления корзины пользователя.
 * @Author: szabolotnyi
 * @version: 1.0.0
 */
@Service
public class CartService {

    private CartRepository cartRepository;
    private DishRepository dishRepository;
    private final Mappers mappers;
    private MessageHelper helper;

    public CartService(CartRepository cartRepository, DishRepository dishRepository, Mappers mappers,
                       MessageHelper helper) {
        this.cartRepository = cartRepository;
        this.dishRepository = dishRepository;
        this.mappers = mappers;
        this.helper = helper;
    }

    /**
     * Извлекает все блюда в корзине, связанные с текущим авторизованным пользователем.
     *
     * @return CartDTO, содержащий список блюд и их общую стоимость.
     */
    public CartDTO findAllDishes() {
        List<DishDTO> dishDTOList = mappers.cartListToDishDTOList(
                cartRepository.findAllByUserId(Utils.getAuthorizedLogin().getId()));
        return CartDTO.builder()
                .dishes(dishDTOList)
                .totalPrice(mappers.calculateTotalPrice(dishDTOList))
                .build();
    }

    /**
     * Сохраняет новый элемент в корзину для текущего авторизованного пользователя.
     *
     * @param id Идентификатор элемента для добавления.
     * @return Сохраненная сущность Cart.
     * @throws NoSuchElementException если указанный идентификатор блюда не существует.
     */
    @Transactional
    public Cart saveNewItem(@NonNull int id) {
        User user = Utils.getAuthorizedLogin();
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        helper.getLogMessage("create.cart.not") + id));
        return cartRepository.save(Cart.builder()
                .user(user)
                .dish(dish)
                .build());
    }

    /**
     * Удаляет элемент из корзины по его идентификатору.
     *
     * @param id Идентификатор элемента для удаления.
     * @throws NoSuchElementException если элемент с указанным идентификатором не существует.
     */
    @Transactional
    public void delete(@NonNull int id) {
        List<Cart> cartList = cartRepository.findCartByDish_Id(id);
        if (cartList.isEmpty()) {
            throw new NoSuchElementException(helper.getLogMessage("delete.cart.not"));
        }
        cartRepository.delete(cartList.get(0));
    }

    /**
     * Удаляет все элементы в корзине, связанные с указанным идентификатором пользователя.
     *
     * @param id Идентификатор пользователя, чьи элементы корзины должны быть удалены.
     */
    @Transactional
    public void deleteByLogin(@NonNull int id) {
        cartRepository.deleteByUserId(id);
    }
}

