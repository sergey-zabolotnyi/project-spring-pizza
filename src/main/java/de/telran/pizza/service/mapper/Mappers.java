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

/**
 * Интерфейс Mappers предоставляет методы для преобразования объектов сущностей в объекты DTO.
 */
@Mapper(componentModel = "spring")
public interface Mappers {

    /**
     * Преобразует список блюд в список DTO блюд.
     *
     * @param dishes Список сущностей блюд.
     * @return Список DTO блюд.
     */
    List<DishDTO> dishesToDishDTOs(List<Dish> dishes);

    /**
     * Преобразует список категорий в список DTO категорий.
     *
     * @param categories Список сущностей категорий.
     * @return Список DTO категорий.
     */
    List<CategoryDTO> categoriesToCategoryDTOs(List<Category> categories);

    /**
     * Преобразует сущность блюда в DTO блюда.
     *
     * @param dish Сущность блюда.
     * @return DTO блюда.
     */
    default DishDTO dishToDishDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .name(mapDish(dish))
                .price(dish.getPrice())
                .category(categoryToDTO(dish.getCategory()))
                .build();
    }

    /**
     * Преобразует список сущностей корзины в список DTO блюд.
     *
     * @param cartList Список сущностей корзины.
     * @return Список DTO блюд.
     */
    default List<DishDTO> cartListToDishDTOList(List<Cart> cartList) {
        return cartList.stream()
                .map(cart -> cart.getDish())
                .map(dish -> dishToDishDTO(dish))
                .collect(Collectors.toList());
    }

    /**
     * Преобразует сущность категории в DTO категории.
     *
     * @param category Сущность категории.
     * @return DTO категории.
     */
    default CategoryDTO categoryToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .category(mapCategory(category))
                .build();
    }

    /**
     * Вычисляет общую стоимость блюд в корзине.
     *
     * @param dishDTOList Список DTO блюд.
     * @return Общая стоимость.
     */
    default BigDecimal calculateTotalPrice(List<DishDTO> dishDTOList) {
        return dishDTOList.stream()
                .map(DishDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Определяет, какое название категории использовать в зависимости от текущей локали.
     *
     * @param category Сущность категории.
     * @return Название категории.
     */
    default String mapCategory(Category category) {
        return Utils.isLocaleEnglish() ? category.getCategoryEn() : category.getCategoryRu();
    }

    /**
     * Определяет, какое название блюда использовать в зависимости от текущей локали.
     *
     * @param dish Сущность блюда.
     * @return Название блюда.
     */
    default String mapDish(Dish dish) {
        return Utils.isLocaleEnglish() ? dish.getNameEn() : dish.getNameRu();
    }

    /**
     * Преобразует список DTO блюд в список сущностей блюд.
     *
     * @param dishes Список DTO блюд.
     * @return Список сущностей блюд.
     */
    List<Dish> orderDishesList(List<DishDTO> dishes);

}

