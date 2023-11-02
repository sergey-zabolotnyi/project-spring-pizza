package de.telran.pizza.service;

import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.service.mapper.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для управления категориями.
 * Этот класс используется для управления категориями приложения.
 * @Author: szabolotnyi
 * @version: 1.0.0
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    public CategoryService(CategoryRepository categoryRepository, Mappers mappers) {
        this.categoryRepository = categoryRepository;
        this.mappers = mappers;
    }

    /**
     * Получает список всех категорий в формате DTO.
     *
     * @return Список объектов CategoryDTO, представляющих категории.
     */
    public List<CategoryDTO> findAllCategory() {
        return mappers.categoriesToCategoryDTOs(categoryRepository.findAll());
    }
}
