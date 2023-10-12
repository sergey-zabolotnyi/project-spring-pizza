package de.telran.pizza.service;

import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.dto.CategoryDTO;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing categories.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Retrieves a list of all categories in DTO format.
     *
     * @return A list of CategoryDTO objects representing categories.
     */
    public List<CategoryDTO> findAllCategory() {
        return categoryListToDtoList(categoryRepository.findAll());
    }

    /**
     * Converts a list of Category entities to a list of CategoryDTOs.
     *
     * @param categoryList A list of Category entities.
     * @return A list of CategoryDTO objects.
     */
    public static List<CategoryDTO> categoryListToDtoList(List<Category> categoryList) {
        return categoryList.stream()
                .map(category -> categoryToDto(category))
                .collect(Collectors.toList());
    }

    /**
     * Converts a Category entity to a CategoryDTO.
     *
     * @param category A Category entity.
     * @return A CategoryDTO object.
     */
    public static CategoryDTO categoryToDto(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .category(Utils.isLocaleEnglish() ? category.getCategoryEn() : category.getCategoryRu())
                .build();
    }

}
