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

    @Schema(description = "Список блюд в корзине")
    private List<DishDTO> dishes;

    @Schema(description = "Список категорий")
    private List<CategoryDTO> categories;

    @Schema(description = "Текущая страница", example = "3")
    private int currentPage;

    @Schema(description = "Всего страниц", example = "10")
    private int totalPages;

    @Schema(description = "Поле для сортировки", example = "Блюдо, Категория")
    private String sortField;

    @Schema(description = "Направление сортировки", example = "asc, desc")
    private String sortDirection;

    @Schema(description = "ID категории", example = "2")
    private Long categoryId;
}
