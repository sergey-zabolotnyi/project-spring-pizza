package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.entity.dto.DishDTO;
import de.telran.pizza.domain.entity.dto.PageDishesDTO;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.repository.DishRepository;
import de.telran.pizza.utils.Utils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Service class for managing dishes.
 */
@Service
public class DishService {
    @Value("${page.size}")
    private int pageSize;
    @Value("${page.sortDefault}")
    private String sortDefault;
    @Value("${page.sortDirectionDefault}")
    private String sortDirectionDefault;
    @Value("${page.category}")
    private int categoryIdDefault;

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageHelper helper;

    /**
     * Gets a paginated list of dishes along with category information.
     *
     * @param pageNum       The page number to retrieve.
     * @param sortField     The field to sort by.
     * @param sortDirection The direction of sorting (ASC or DESC).
     * @param categoryId    The ID of the category to filter by (default if null or negative).
     * @return A PageDishesDTO containing the paginated list of dishes, category information,
     * current page, total pages, sort field, sort direction, and category ID.
     */
    @Transactional
    public PageDishesDTO findAllDishesPage(Integer pageNum, String sortField,
                                           String sortDirection, Long categoryId) {
        categoryId = categoryId == null || categoryId < 0 ? categoryIdDefault : categoryId;
        Sort sort = sortDefaultAndValidate(sortField, sortDirection);

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        Page<Dish> page = (categoryId == 0)
                ? dishRepository.findAll(pageable)
                : dishRepository.findByCategoryId(categoryId, pageable);

        List<Category> categories = categoryRepository.findAll();

        return PageDishesDTO.builder()
                .dishes(dishListToDtoList(page.getContent()))
                .categories(CategoryService.categoryListToDtoList(categories))
                .currentPage(pageNum)
                .totalPages(page.getTotalPages())
                .sortField(sortField)
                .sortDirection(sortDirection)
                .categoryId(categoryId)
                .build();
    }

    /**
     * Validates and sets default values for sorting parameters.
     *
     * @param sortField     The field to sort by.
     * @param sortDirection The direction of sorting (ASC or DESC).
     * @return A Sort object with validated and default values for sorting.
     */
    private Sort sortDefaultAndValidate(String sortField, String sortDirection) {
        sortField = sortField == null || sortField.equals("null")
                ? sortDefault : (sortField.equals("name")) ? helper.getLogMessage("db.name") : sortField;
        sortDirection = sortDirection != null &&
                (sortDirection.equalsIgnoreCase("asc") ||
                        sortDirection.equalsIgnoreCase("desc"))
                ? sortDirection
                : sortDirectionDefault;
        return sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }

    /**
     * Converts a list of Dish entities to a list of DishDTO data transfer objects.
     *
     * @param dishList The list of Dish entities to be converted.
     * @return A list of DishDTO data transfer objects.
     */
    private static List<DishDTO> dishListToDtoList(List<Dish> dishList) {
        return dishList.stream()
                .map(dish -> dishToDishDto(dish))
                .collect(Collectors.toList());
    }

    /**
     * Converts a Dish entity to a DishDTO data transfer object.
     *
     * @param dish The Dish entity to be converted.
     * @return A DishDTO data transfer object representing the provided Dish entity.
     */
    private static DishDTO dishToDishDto(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .name(Utils.isLocaleEnglish() ? dish.getNameEn() : dish.getNameRu())
                .price(dish.getPrice())
                .category(CategoryService.categoryToDto(dish.getCategory()))
                .build();
    }

    /**
     * Gets a list of all dishes, sorted by ID in ascending order.
     *
     * @return A list of DishDTOs representing the dishes.
     */
    public List<DishDTO> findAllDishes() {
        Sort sort = Sort.by("id").ascending();
        return dishListToDtoList(dishRepository.findAll(sort));
    }

    /**
     * Finds a dish by its ID.
     *
     * @param id The ID of the dish to be retrieved.
     * @return The Dish entity with the specified ID.
     * @throws NoSuchElementException if a dish with the specified ID does not exist.
     */
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(helper.getLogMessage("select.dish.not")));
    }

    /**
     * Saves a new dish to the database.
     *
     * @param dish The Dish entity to be saved.
     * @return The saved Dish entity.
     * @throws NoSuchElementException if the specified category ID does not exist.
     */
    @Transactional
    public Dish saveNewDish(@NonNull Dish dish) throws NoSuchElementException {
        dish.setTime(LocalDateTime.now());
        dish.setCategory(categoryRepository.findById(dish.getCategory().getId()).orElseThrow(
                () -> new NoSuchElementException(helper.getLogMessage("find.dish.not"))
        ));
        return dishRepository.save(dish);
    }

    /**
     * Updates an existing dish in the database.
     *
     * @param dish The Dish entity to be updated.
     * @throws NoSuchElementException if the specified dish does not exist.
     */
    public void update(@NonNull Dish dish) {
        dish.setTime(LocalDateTime.now());
        dishRepository.save(dish);
    }

    /**
     * Deletes a dish by its ID.
     *
     * @param id The ID of the dish to be deleted.
     */
    public void delete(@NonNull Long id) {
        dishRepository.deleteById(id);
    }
}
