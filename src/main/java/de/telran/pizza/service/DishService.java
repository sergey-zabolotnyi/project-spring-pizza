package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.dto.PageDishesDTO;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.repository.DishRepository;
import de.telran.pizza.service.mapper.CategoryMapper;
import de.telran.pizza.service.mapper.DishMapper;
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

    private DishRepository dishRepository;
    private CategoryRepository categoryRepository;
    private MessageHelper helper;

    public DishService(DishRepository dishRepository, CategoryRepository categoryRepository, MessageHelper helper) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
        this.helper = helper;
    }

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
                                           String sortDirection, Long categoryId){
        categoryId = categoryId == null || categoryId < 0 ? categoryIdDefault : categoryId;
        Sort sort = validationSetDefault(sortField, sortDirection);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        Page<Dish> page = (categoryId == 0)
                ? dishRepository.findAll(pageable)
                : dishRepository.findByCategoryId(categoryId, pageable);

        List<Category> categories = categoryRepository.findAll();

        return PageDishesDTO.builder()
                .dishes(DishMapper.dishListToDtoList(page.getContent()))
                .categories(CategoryMapper.categoryListToDtoList(categories))
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
    private Sort validationSetDefault(String sortField, String sortDirection) {
        sortField = sortField == null || sortField.equals("null")
                ? sortDefault
                : (sortField.equals("name")) ? helper.getMessage("db.name") : sortField;
        sortDirection = sortDirection != null &&
                (sortDirection.equalsIgnoreCase("asc") ||
                        sortDirection.equalsIgnoreCase("desc"))
                ? sortDirection
                : sortDirectionDefault;
        return sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }

    /**
     * Gets a list of all dishes, sorted by ID in ascending order.
     *
     * @return A list of DishDTOs representing the dishes.
     */
    public List<DishDTO> findAllDishes() {
        Sort sort = Sort.by("id").ascending();
        return DishMapper.dishListToDtoList(dishRepository.findAll(sort));
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
