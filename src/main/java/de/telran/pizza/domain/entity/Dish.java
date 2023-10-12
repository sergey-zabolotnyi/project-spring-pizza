package de.telran.pizza.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a dish entity in the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dish")
@Schema(description = "Объект блюда пиццерии")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор блюда", example = "5")
    private Long id;

    @Pattern(regexp = "^[a-zA-z0-9 -]+$",
            message = "error.dish.name_en")
    @Column(name = "name_en")
    @Schema(description = "Название блюда на английском", example = "Pizza Quattro Formaggi")
    private String nameEn;

    @Pattern(regexp = "^[а-яА-ЯёЁ0-9 -]+$",
            message = "error.dish.name_ru")
    @Column(name = "name_ru")
    @Schema(description = "Название блюда на русском", example = "Пицца 4 сыра")
    private String nameRu;

    @Min(value = 1 / 100L, message = "error.dish.price")
    @Column(nullable = false)
    @Schema(description = "Стоимость блюда", example = "10.50")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @Schema(description = "ID категории", example = "8")
    private Category category;

    @Schema(description = "Время добавления записи", example = "2023-10-05 15:05:34")
    private LocalDateTime time;
}

