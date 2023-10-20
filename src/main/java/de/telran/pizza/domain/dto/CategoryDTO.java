package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a category of a pizzeria.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ДТО объект категории Пиццерии")
public class CategoryDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор категории", example = "2")
    private Long id;

    @Schema(description = "Название категории", example = "Пиццы")
    private String category;
}
