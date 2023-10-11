package de.telran.pizza.domain.entity;

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
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "^[a-zA-z0-9 -]+$",
            message = "error.dish.name_en")
    @Column(name = "name_en")
    private String nameEn;

    @Pattern(regexp = "^[а-яА-ЯёЁ0-9 -]+$",
            message = "error.dish.name_ru")
    @Column(name = "name_ru")
    private String nameRu;

    @Min(value = 1 / 100L, message = "error.dish.price")
    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    private LocalDateTime time;
}

