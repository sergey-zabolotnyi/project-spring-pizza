package de.telran.pizza.service.mapper;

import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.dto.DishDTO;
import de.telran.pizza.utils.Utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DishMapper {
    /**
     * Converts a list of Dish entities to a list of DishDTO data transfer objects.
     *
     * @param dishList The list of Dish entities to be converted.
     * @return A list of DishDTO data transfer objects.
     */
    public static List<DishDTO> dishListToDtoList(List<Dish> dishList) {
        return dishList.stream()
                .map(dish -> dishToDishDto(dish))
                .collect(Collectors.toList());
    }

    /**
     * Converts a Dish entity to a DishDTO data transfer object.
     *
     * @param dish The Dish entity to be converted.
     * @return A DishDTO data transfer object representing the provided Dish entity.
     */
    public static DishDTO dishToDishDto(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .name(Utils.isLocaleEnglish() ? dish.getNameEn() : dish.getNameRu())
                .price(dish.getPrice())
                .category(CategoryMapper.categoryToDto(dish.getCategory()))
                .build();
    }

    /**
     * Calculates the total price of a list of dishes.
     *
     * @param dishDTOList The list of DishDTO objects.
     * @return The total price of all dishes in the list.
     */
    public static BigDecimal getDishTotalPrice(List<DishDTO> dishDTOList) {
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
    public static List<DishDTO> listDishesDTOtoCart(List<Cart> cartList) {
        return cartList.stream()
                .map(cart -> cart.getDish())
                .map(dish -> dishToDishDto(dish))
                .collect(Collectors.toList());
    }
}
