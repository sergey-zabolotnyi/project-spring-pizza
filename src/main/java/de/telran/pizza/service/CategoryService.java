package de.telran.pizza.service;

import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.service.mapper.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing categories.
 */
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private final Mappers mappers;

    public CategoryService(CategoryRepository categoryRepository, Mappers mappers) {
        this.categoryRepository = categoryRepository;
        this.mappers = mappers;
    }

    /**
     * Gets a list of all categories in DTO format.
     *
     * @return A list of CategoryDTO objects representing categories.
     */
    public List<CategoryDTO> findAllCategory() {
        return mappers.categoriesToCategoryDTOs(categoryRepository.findAll());
    }
}
