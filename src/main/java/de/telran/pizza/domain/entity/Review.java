package de.telran.pizza.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Представляет сущность "Отзывы" в приложении.
 * Этот объект используется для хранения данных об отзывах пользователя.
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
@Schema(description = "Объект корзины пользователя")
public class Review {

    /**
     * Идентификатор отзыва.
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор отзыва", example = "1")
    private int id;

    /**
     * Пользователь, владеющий корзиной.
     *
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Schema(description = "ID пользователя", example = "2")
    private User user;

    /**
     * Текст отзыва.
     *
     * @since 1.0.0
     */
    @Column(name = "text", length = 1000)
    @Schema(description = "Текст отзыва", example = "отличная пиццерия... рекомендую!")
    @NotEmpty(message = "review.not.empty")
    @Size(max = 1000, message = "review.length")
    private String text;

    /**
     * Оценка от 1 до 10.
     *
     * @since 1.1.0
     */
    @Column(name = "rating")
    @Schema(description = "Оценка от 1 до 10", example = "9")
    @Min(value = 1, message = "rating.min")
    @Max(value = 10, message = "rating.max")
    private int rating;

    /**
     * Время создания отзыва.
     *
     * @since 1.2.0
     */
    @Column(name = "time")
    @Schema(description = "Время добавления записи", example = "2023-10-05 15:05:34")
    private LocalDateTime time;

}
