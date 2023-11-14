package de.telran.pizza.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Класс представляет объект категории пиццерии.
 * Этот объект используется для хранения данных о категориях приложения.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Schema(description = "Объект категории пиццерии")
public class Category {

    /**
     * Идентификатор категории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор категории", example = "4")
    private int id;

    /**
     * Название категории на английском.
     */
    @Column(name = "category_en")
    @Schema(description = "Название категории на английском", example = "Pizza")
    private String categoryEn;

    /**
     * Название категории на русском.
     */
    @Column(name = "category_ru")
    @Schema(description = "Название категории на русском", example = "Пицца")
    private String categoryRu;
}
