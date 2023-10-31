package de.telran.pizza.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс, представляющий Data Transfer Object (DTO) для информации о корзине пользователя.
 * Этот объект используется для передачи данных о корзине пользователя между различными компонентами приложения.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ДТО объект корзины пользователя")
public class CartDTO {

    /**
     * Список блюд в корзине
     */
    @Schema(description = "Список блюд в корзине")
    private List<DishDTO> dishes;

    /**
     * Общая стоимость блюд в корзине пользователя
     */
    @Schema(description = "Общая стоимость блюд в корзине пользователя")
    private BigDecimal totalPrice;
}
