package de.telran.pizza.service.mapper;

import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.entity.Cart;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.utils.Utils;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface Mappers {

    List<DishDTO> dishesToDishDTOs(List<Dish> dishes);

    List<CategoryDTO> categoriesToCategoryDTOs(List<Category> categories);

    default DishDTO dishToDishDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .name(mapDish(dish))
                .price(dish.getPrice())
                .category(categoryToDTO(dish.getCategory()))
                .build();
    }

    default List<DishDTO> cartListToDishDTOList(List<Cart> cartList) {
        return cartList.stream()
                .map(cart -> cart.getDish())
                .map(dish -> dishToDishDTO(dish))
                .collect(Collectors.toList());
    }

    default CategoryDTO categoryToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .category(mapCategory(category))
                .build();
    }

    default BigDecimal calculateTotalPrice(List<DishDTO> dishDTOList) {
        return dishDTOList.stream()
                .map(DishDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default String mapCategory(Category category) {
        return Utils.isLocaleEnglish() ? category.getCategoryEn() : category.getCategoryRu();
    }

    default String mapDish(Dish dish) {
        return Utils.isLocaleEnglish() ? dish.getNameEn() : dish.getNameRu();
    }
}
