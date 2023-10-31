package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий Data Transfer Object (DTO) для информации о категориях.
 * Этот объект используется для передачи данных о категориях между различными компонентами приложения.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Data
@Builder
@Schema(description = "ДТО объект категории Пиццерии")
public class CategoryDTO {

    /**
     * Идентификатор категории ДТО
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор категории", example = "2")
    private int id;

    /**
     * Название категории ДТО
     */
    @Schema(description = "Название категории", example = "Пиццы")
    private String category;

    /**
     * Пустой конструктор для создания категории ДТО
     * @since    1.0.0
     */
    public CategoryDTO() {
    }

    /**
     * Конструктор для создания категории ДТО
     *
     * @param id            идентификатор категории ДТО
     * @param category      название категории ДТО
     * @since               1.0.0
     */
    public CategoryDTO(int id, String category) {
        this.id = id;
        this.category = category;
    }
}
