package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс представляет объект DTO для блюд и категорий, предназначенных для главной страницы приложения.
 * Этот объект используется для передачи данных о блюдах и категориях на главной страницы приложения.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "ДТО объект блюд и категорий для главной страницы приложения")
public class PageDishesDTO {

    /**
     * Список блюд в корзине.
     */
    @Schema(description = "Список блюд в корзине")
    private List<DishDTO> dishes;

    /**
     * Список категорий.
     */
    @Schema(description = "Список категорий")
    private List<CategoryDTO> categories;

    /**
     * Текущая страница.
     *
     * @example 3
     */
    @Schema(description = "Текущая страница", example = "3")
    private int currentPage;

    /**
     * Всего страниц.
     *
     * @example 10
     */
    @Schema(description = "Всего страниц", example = "10")
    private int totalPages;

    /**
     * Поле для сортировки.
     *
     * @example "Блюдо, Категория"
     */
    @Schema(description = "Поле для сортировки", example = "Блюдо, Категория")
    private String sortField;

    /**
     * Направление сортировки.
     *
     * @example "asc, desc"
     */
    @Schema(description = "Направление сортировки", example = "asc, desc")
    private String sortDirection;

    /**
     * ID категории.
     *
     * @example 2
     */
    @Schema(description = "ID категории", example = "2")
    private int categoryId;
}
