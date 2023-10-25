package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO object representing dishes in the pizzeria.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ДТО объект блюд в Пиццерии")
public class DishDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор категории", example = "10")
    private int id;

    @Schema(description = "Название блюда", example = "Маргарита")
    private String name;

    @Schema(description = "Стоимость блюда", example = "10.50")
    private BigDecimal price;

    @Schema(description = "Название категории", example = "Пиццы")
    private CategoryDTO category;
}

