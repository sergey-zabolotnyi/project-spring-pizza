package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.dto.CategoryDTO;
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
 * Controller for managing various categories in the pizzeria.
 */
@RestController
@Slf4j
@RequestMapping("/api/categories")
@Tag(name = "Контроллер категорий", description = "Контроллер для различных категорий пиццерии")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MessageHelper helper;

    /**
     * Gets all categories.
     * @return A list of CategoryDTO representing all categories.
     */
    @Operation(summary = "Получение всех категорий пиццерии",
            description = "Получение всех категорий Пиццерии, хранящихся в БД")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved categories.")
    @GetMapping("/get")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        log.info(helper.getLogMessage("all.categories.log"));
        return ResponseEntity.ok(categoryService.findAllCategory());
    }
}
