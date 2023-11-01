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
 * Represents an order entity in the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Schema(description = "Объект заказов пиццерии")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор заказа", example = "7")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    @Schema(description = "ID пользователя", example = "7")
    private Login login;

    @Min(value = 0, message = "error.orders.totalPrice")
    @Column(nullable = false)
    @Schema(description = "Общая стоимость заказа", example = "45.99")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Статус заказа", example = "NEW, DELIVERED, DONE")
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Время добавления записи", example = "2023-10-05 15:05:34")
    private LocalDateTime time;

    @ManyToMany
    @JoinTable(
            name = "order_dish",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;
}
