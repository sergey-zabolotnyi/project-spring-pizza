package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CartDTO;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.Login;
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
 * Service class for managing shopping carts.
 */
@Service
public class CartService {
    private CartRepository cartRepository;
    private DishRepository dishRepository;
    private final Mappers mappers;
    private MessageHelper helper;

    public CartService(CartRepository cartRepository, DishRepository dishRepository, Mappers mappers, MessageHelper helper) {
        this.cartRepository = cartRepository;
        this.dishRepository = dishRepository;
        this.mappers = mappers;
        this.helper = helper;
    }

    /**
     * Retrieves all dishes in the cart associated with the logged-in user.
     *
     * @return CartDTO containing the list of dishes and their total price.
     */
    public CartDTO findAllDishes() {
        List<DishDTO> dish = mappers.cartListToDishDTOList(
                cartRepository.findAllByLoginId(Utils.getAuthorizedLogin().getId()));
        return CartDTO.builder()
                .dishes(dish)
                .totalPrice(mappers.calculateTotalPrice(dish))
                .build();
    }

    /**
     * Saves a new item to the cart for the logged-in user.
     *
     * @param id The ID of the item to be added.
     * @return The saved Cart entity.
     * @throws NoSuchElementException if the specified dish ID does not exist.
     */
    @Transactional
    public Cart saveNewItem(@NonNull int id) {
        Login user = Utils.getAuthorizedLogin();
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        helper.getLogMessage("create.cart.not") + id));
        return cartRepository.save(Cart.builder()
                .login(user)
                .dish(dish)
                .build());
    }

    /**
     * Deletes an item from the cart by its ID.
     *
     * @param id The ID of the item to be deleted.
     * @throws NoSuchElementException if the item with the specified ID does not exist.
     */
    @Transactional
    public void delete(@NonNull int id) {
        List<Cart> list = cartRepository.findCartByDish_Id(id);
        if (list.isEmpty()) {
            throw new NoSuchElementException(helper.getLogMessage("delete.cart.not"));
        }
        cartRepository.delete(list.get(0));
    }

    /**
     * Deletes all items in the cart associated with the specified user ID.
     *
     * @param id The ID of the user whose cart items should be deleted.
     */
    @Transactional
    public void deleteByLogin(@NonNull int id) {
        cartRepository.deleteByLoginId(id);
    }
}

