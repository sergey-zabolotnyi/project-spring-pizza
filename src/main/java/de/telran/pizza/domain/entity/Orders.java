package de.telran.pizza.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.telran.pizza.domain.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс представляет объект заказов пиццерии.
 * Этот объект используется для передачи данных о заказах приложения.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Schema(description = "Объект заказов пиццерии")
public class Orders {

    /**
     * Идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор заказа", example = "7")
    private int id;

    /**
     * Пользователь, совершивший заказ.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Schema(description = "ID пользователя", example = "7")
    private User user;

    /**
     * Общая стоимость заказа.
     */
    @Min(value = 0, message = "error.orders.totalPrice")
    @Column(nullable = false)
    @Schema(description = "Общая стоимость заказа", example = "45.99")
    private BigDecimal totalPrice;

    /**
     * Статус заказа.
     */
    @Enumerated(EnumType.STRING)
    @Schema(description = "Статус заказа", example = "NEW, DELIVERED, DONE")
    private Status status;

    /**
     * Время добавления записи.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Время добавления записи", example = "2023-10-05 15:05:34")
    private LocalDateTime time;

    /**
     * Список блюд в заказе.
     */
    @ManyToMany
    @JoinTable(
            name = "order_dish",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes;
}
