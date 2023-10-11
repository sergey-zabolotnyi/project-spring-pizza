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
import de.telran.pizza.utils.Utils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        List<DishDTO> dish = dishesDTOtoCart(
                cartRepository.findAllByLoginId(Utils.getAuthorizedLogin().getId()));
        return CartDTO.builder()
                .dishes(dish)
                .totalPrice(getDishTotalPrice(dish))
                .build();
    }

    /**
     * Calculates the total price of a list of dishes.
     *
     * @param dishDTOList The list of DishDTO objects.
     * @return The total price of all dishes in the list.
     */
    private BigDecimal getDishTotalPrice(List<DishDTO> dishDTOList) {
        return dishDTOList.stream()
                .map(dishDTO -> dishDTO.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Converts a list of Cart objects to a list of DishDTO objects.
     *
     * @param cartList The list of Cart objects.
     * @return List of DishDTO objects.
     */
    private List<DishDTO> dishesDTOtoCart(List<Cart> cartList) {
        return cartList.stream()
                .map(cart -> cart.getDish())
                .map(dish -> dishToDishDto(dish))
                .collect(Collectors.toList());
    }

    /**
     * Converts a Dish object to a DishDTO object.
     *
     * @param dish The Dish object to be converted.
     * @return The resulting DishDTO object.
     */
    private DishDTO dishToDishDto(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .name(Utils.isLocaleEnglish() ? dish.getNameEn() : dish.getNameRu())
                .price(dish.getPrice())
                .category(CategoryService.categoryToDto(dish.getCategory()))
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

