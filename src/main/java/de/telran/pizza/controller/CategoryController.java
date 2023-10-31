package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для управления различными категориями в пиццерии.
 */
@RestController
@Slf4j
@RequestMapping("/api/categories")
@Tag(name = "Контроллер категорий", description = "Контроллер для различных категорий пиццерии")
public class CategoryController {

    private CategoryService categoryService;
    private MessageHelper helper;

    public CategoryController(CategoryService categoryService, MessageHelper helper) {
        this.categoryService = categoryService;
        this.helper = helper;
    }

    /**
     * Получает все категории.
     * @return Список объектов CategoryDTO, представляющих все категории.
     */
    @Operation(summary = "Получение всех категорий пиццерии",
            description = "Получение всех категорий Пиццерии, хранящихся в БД")
    @ApiResponse(responseCode = "200", description = "Успешно получены категории.")
    @GetMapping("/get")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        log.info(helper.getLogMessage("all.categories.log"));
        return ResponseEntity.ok(categoryService.findAllCategory());
    }
    /**
     * Получает количество категорий.
     * @return ResponseEntity, содержащий количество категорий.
     */
    @Operation(summary = "Получение количества всех категорий пиццерии",
            description = "Получение количества всех категорий Пиццерии, хранящихся в БД")
    @ApiResponse(responseCode = "200", description = "Успешно получено количество категорий.")
    @GetMapping("/count")
    public ResponseEntity<Integer> getCategoriesCount() {
        log.info(helper.getLogMessage("all.categories.count.log"));
        return ResponseEntity.ok(categoryService.findAllCategory().size());
    }
}