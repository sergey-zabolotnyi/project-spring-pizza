package de.telran.pizza.domain.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "ДТО объект блюд и категорий для главной страницы приложения")
public class PageDishesDTO {
    /**
     * List of DishDTO representing dishes.
     */
    private List<DishDTO> dishes;

    /**
     * List of CategoryDTO representing categories.
     */
    private List<CategoryDTO> categories;

    /**
     * The current page number.
     */
    private int currentPage;

    /**
     * The total number of pages.
     */
    private int totalPages;

    /**
     * The field used for sorting.
     */
    private String sortField;

    /**
     * The direction of sorting (ascending or descending).
     */
    private String sortDirection;

    /**
     * The ID of the selected category for filtering.
     */
    private Long categoryId;
}
