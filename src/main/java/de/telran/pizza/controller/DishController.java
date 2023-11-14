package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для управления блюдами.
 */
@Slf4j
@RestController
@RequestMapping("api/manager/dishes")
@Tag(name = "Контроллер для блюд", description = "Контроллер для различных блюд пиццерии")
public class DishController {

    private DishService dishService;
    private MessageHelper helper;

    /**
     * Контроллер блюд (конструктор).
     *
     * @param dishService Сервис управления блюдами.
     * @param helper      Вспомогательный класс для работы с сообщениями.
     */
    public DishController(DishService dishService, MessageHelper helper) {
        this.dishService = dishService;
        this.helper = helper;
    }

    /**
     * Получает список всех блюд.
     *
     * @return ResponseEntity, содержащий список объектов DishDTO.
     */
    @GetMapping("/get_all")
    @Operation(
            summary = "Получение всех блюд пиццерии",
            description = "Получение всех блюд Пиццерии, хранящихся в БД")
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        log.info(helper.getLogMessage("all.dishes.log"));
        return ResponseEntity.ok(dishService.findAllDishes());
    }

    /**
     * Создает новое блюдо на основе предоставленной сущности Dish.
     *
     * @param dish Сущность Dish, которая будет создана.
     * @return ResponseEntity, содержащий созданное блюдо.
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание новых блюд",
            description = "Создание новых блюд пиццерии")
    public ResponseEntity<Dish> create(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Найменование блюд") Dish dish) {
        log.info(helper.getLogMessage("create.dish.log"));
        try {
            return ResponseEntity.ok(dishService.saveNewDish(dish));
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    helper.getLogMessage("create.dish.not") + dish.getNameEn());
        }
    }

    /**
     * Получает блюдо по его ID.
     *
     * @param id ID блюда, которое нужно получить.
     * @return ResponseEntity, содержащий сущность Dish с указанным ID.
     */
    @GetMapping("/update")
    @Operation(
            summary = "Получение блюда по ID",
            description = "Получение блюд Пиццерии по ID, хранящихся в БД")
    public ResponseEntity<Dish> getDishById(@Valid @RequestParam(value = "id")
                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                    description = "Идентефикатор блюда") int id) {
        log.info(helper.getLogMessage("select.dishes.log") + id);
        try {
            return ResponseEntity.ok(dishService.findById(id));
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, helper.getLogMessage("select.dishes.not") + id);
        }
    }

    /**
     * Обновляет существующее блюдо на основе предоставленной сущности Dish.
     *
     * @param dish Сущность Dish, которая будет обновлена.
     */
    @PutMapping("/update")
    @Operation(
            summary = "Изменить блюдо",
            description = "Изменение блюд пиццерии, хранящихся в БД")
    public void update(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Найменование блюд") Dish dish) {
        log.info(helper.getLogMessage("update.dishes.log") + dish.getId());
        try {
            dishService.update(dish);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    helper.getLogMessage("update.dishes.not"));
        }
    }

    /**
     * Удаляет блюдо на основе предоставленной сущности ItemDTO.
     *
     * @param id идентификатор удаляемого блюда.
     */
    @DeleteMapping("/delete")
    @Operation(
            summary = "Удалить блюдо",
            description = "Удаление блюд пиццерии, хранящихся в БД")
    public void delete(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Идентификатор блюда") int id) {
        log.info(helper.getLogMessage("delete.dishes.log") + id);
        try {
            dishService.delete(id);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    helper.getLogMessage("delete.dishes.not"));
        }
    }
    /**
     * Получает количество блюд для Статистики.
     * @return ResponseEntity, содержащий количество категорий.
     */
    @Operation(
            summary = "Получение количества блюд в меню",
            description = "Получение количества блюд в меню, хранящихся в БД")
    @GetMapping("/get_count")
    @ResponseBody // Добавляем аннотацию, чтобы возвращать тело ответа напрямую
    public ResponseEntity<Integer> getAllDishesCount() {
        log.info(helper.getLogMessage("all.dishes.count.log"));
        return ResponseEntity.ok(dishService.findAllDishes().size());
    }
}
