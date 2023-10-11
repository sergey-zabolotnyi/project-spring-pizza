package de.telran.pizza.domain.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ДТО объект корзины пользователя")
public class CartDTO {

    @Schema(description = "Список блюд в корзине")
    private List<DishDTO> dishes;

    @Schema(description = "Общая стоимость блюд в корзине пользователя")
    private BigDecimal totalPrice;
}
