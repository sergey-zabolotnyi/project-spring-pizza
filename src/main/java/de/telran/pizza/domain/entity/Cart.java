package de.telran.pizza.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * Представляет сущность "Корзина" в приложении.
 * Этот объект используется для хранения данных о корзине пользователя.
 */
@Data
@Builder
@Entity
@Table(name = "cart")
@Schema(description = "Объект корзины пользователя")
public class Cart {

    /**
     * Идентификатор корзины.
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор корзины", example = "1")
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
     * Блюдо, добавленное в корзину.
     *
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    @Schema(description = "ID блюда", example = "3")
    private Dish dish;

    /**
     * Создает новый экземпляр корзины.
     */
    public Cart() {
    }

    /**
     * Создает новый экземпляр корзины с заданными параметрами.
     *
     * @param id    Идентификатор корзины.
     * @param user Пользователь, владеющий корзиной.
     * @param dish  Блюдо, добавленное в корзину.
     */
    public Cart(int id, User user, Dish dish) {
        this.id = id;
        this.user = user;
        this.dish = dish;
    }
}