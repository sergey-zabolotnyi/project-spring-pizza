package de.telran.pizza.service;

import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.service.mapper.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для управления категориями.
 * Этот класс используется для управления категориями приложения.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    /**
     * Сервис управления категориями (конструктор).
     *
     * @param categoryRepository Репозиторий категорий.
     * @param mappers            Мапперы для преобразования данных.
     */
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
