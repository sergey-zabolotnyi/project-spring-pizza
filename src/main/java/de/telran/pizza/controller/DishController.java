package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.dto.DishDTO;
import de.telran.pizza.domain.entity.dto.ItemDTO;
import de.telran.pizza.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for managing dishes.
 */
@Slf4j
@RestController
@RequestMapping("api/manager/dishes")
@Tag(name = "Контроллер для блюд", description = "Контроллер для различных блюд пиццерии")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private MessageHelper helper;

    /**
     * Gets a list of all dishes.
     *
     * @return A ResponseEntity containing a list of DishDTOs.
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
     * Creates a new dish based on the provided Dish entity.
     *
     * @param dish The Dish entity to be created.
     * @return A ResponseEntity containing the created Dish entity.
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
     * Gets a dish by its ID.
     *
     * @param id The ID of the dish to be retrieved.
     * @return A ResponseEntity containing the Dish entity with the specified ID.
     */
    @GetMapping("/update")
    @Operation(
            summary = "Получение блюда по ID",
            description = "Получение блюд Пиццерии по ID, хранящихся в БД")
    public ResponseEntity<Dish> getDishById(@Valid @RequestParam(value = "id")
                                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                    description = "Идентефикатор блюда") Long id) {
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
     * Updates an existing dish based on the provided Dish entity.
     *
     * @param dish The Dish entity to be updated.
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
     * Deletes a dish based on the provided ItemDTO.
     *
     * @param itemDTO The ItemDTO containing the ID of the dish to be deleted.
     */
    @DeleteMapping("/delete")
    @Operation(
            summary = "Удалить блюдо",
            description = "Удаление блюд пиццерии, хранящихся в БД")
    public void delete(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Найменование блюд") ItemDTO itemDTO) {
        log.info(helper.getLogMessage("delete.dishes.log") + itemDTO.getItemId());
        try {
            dishService.delete(itemDTO.getItemId());
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    helper.getLogMessage("delete.dishes.not"));
        }
    }
    /**
     * Gets dishes count for Statistics.
     *
     * @return A ResponseEntity containing a dishes count in menu.
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
