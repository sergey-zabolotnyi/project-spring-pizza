package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.Login;
import de.telran.pizza.domain.entity.dto.CartDTO;
import de.telran.pizza.domain.entity.dto.DishDTO;
import de.telran.pizza.domain.entity.dto.ItemDTO;
import de.telran.pizza.repository.CartRepository;
import de.telran.pizza.repository.DishRepository;
import de.telran.pizza.service.mapper.DishMapper;
import de.telran.pizza.utils.Utils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing shopping carts.
 */
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MessageHelper helper;

    /**
     * Retrieves all dishes in the cart associated with the logged-in user.
     *
     * @return CartDTO containing the list of dishes and their total price.
     */
    public CartDTO findAllDishes() {
        List<DishDTO> dish = DishMapper.listDishesDTOtoCart(
                cartRepository.findAllByLoginId(Utils.getAuthorizedLogin().getId()));
        return CartDTO.builder()
                .dishes(dish)
                .totalPrice(DishMapper.getDishTotalPrice(dish))
                .build();
    }

    /**
     * Saves a new item to the cart for the logged-in user.
     *
     * @param itemDTO The ItemDTO containing the details of the item to be added.
     * @return The saved Cart entity.
     * @throws NoSuchElementException if the specified dish ID does not exist.
     */
    @Transactional
    public Cart saveNewItem(@NonNull ItemDTO itemDTO) {
        Login user = Utils.getAuthorizedLogin();
        Dish dish = dishRepository.findById(itemDTO.getItemId())
                .orElseThrow(() -> new NoSuchElementException(
                        helper.getLogMessage("create.cart.not") + itemDTO.getItemId()));
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
    public void delete(@NonNull Long id) {
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
    public void deleteByLogin(@NonNull Long id) {
        cartRepository.deleteByLoginId(id);
    }
}

