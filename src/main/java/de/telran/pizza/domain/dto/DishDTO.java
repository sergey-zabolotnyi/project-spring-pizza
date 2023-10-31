package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс, представляющий Data Transfer Object (DTO) для информации о блюдах.
 * Этот объект используется для передачи данных о блюдах между различными компонентами приложения.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ДТО объект блюд в Пиццерии")
public class DishDTO {

    /**
     * Идентификатор блюда.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор категории", example = "10")
    private int id;

    /**
     * Название блюда
     */
    @Schema(description = "Название блюда", example = "Маргарита")
    private String name;

    /**
     * Стоимость блюда
     */
    @Schema(description = "Стоимость блюда", example = "10.50")
    private BigDecimal price;

    /**
     * Название категории
     */
    @Schema(description = "Название категории", example = "Пиццы")
    private CategoryDTO category;
}

