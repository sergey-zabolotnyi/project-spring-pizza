package de.telran.pizza.service;

import de.telran.pizza.domain.dto.CategoryDTO;
import de.telran.pizza.service.mapper.CategoryMapper;
import de.telran.pizza.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing categories.
 */
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Gets a list of all categories in DTO format.
     *
     * @return A list of CategoryDTO objects representing categories.
     */
    public List<CategoryDTO> findAllCategory() {
        return CategoryMapper.categoryListToDtoList(categoryRepository.findAll());
    }
}
