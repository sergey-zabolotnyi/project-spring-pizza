package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.PageDishesDTO;
import de.telran.pizza.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * Контроллер для управления основными операциями.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Контроллер главной страницы", description = "Контроллер для главной страницы пиццерии")
public class MainController {
    private DishService dishService;
    private MessageHelper helper;
    public MainController(DishService dishService, MessageHelper helper) {
        this.dishService = dishService;
        this.helper = helper;
    }
    /**
     * Получает список блюд с пагинацией на основе предоставленного номера страницы, поля сортировки, направления сортировки и ID категории.
     *
     * @param pageNum       Номер страницы для пагинации.
     * @param sortField     Поле, по которому будет производиться сортировка блюд.
     * @param sortDirection Направление сортировки (asc или desc).
     * @param categoryId    ID категории для фильтрации.
     * @return ResponseEntity, содержащий объект PageDishesDTO с пагинированным списком блюд.
     * @throws ResponseStatusException в случае ошибки во время процесса получения данных.
     */
    @GetMapping("/get/{page}")
    @Operation(
            summary = "Получение таблицы блюд пиццерии",
            description = "Получение таблицы блюд пиццерии на главной странице")
    public ResponseEntity<PageDishesDTO> findPaginated
    (@Valid @PathVariable(value = "page") @Parameter(description = "Номер страницы") int pageNum,
     @RequestParam(value = "sort", required = false) @io.swagger.v3.oas.annotations.parameters.RequestBody(
             description = "Сортировка по полю") String sortField,
     @RequestParam(value = "direct", required = false) @io.swagger.v3.oas.annotations.parameters.RequestBody(
             description = "Тип сортировки") String sortDirection,
     @RequestParam(value = "category", required = false) @io.swagger.v3.oas.annotations.parameters.RequestBody(
             description = "Категория") int categoryId) {
        log.info(helper.getLogMessage("select.all.dishes.page") + pageNum);

        try {
            return ResponseEntity.ok(dishService.findAllDishesPage(pageNum, sortField, sortDirection, categoryId));
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}

