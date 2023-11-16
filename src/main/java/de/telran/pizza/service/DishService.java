package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.dto.DishDTO;
import de.telran.pizza.domain.dto.PageDishesDTO;
import de.telran.pizza.domain.entity.Category;
import de.telran.pizza.domain.entity.Dish;
import de.telran.pizza.repository.CategoryRepository;
import de.telran.pizza.repository.DishRepository;
import de.telran.pizza.service.mapper.Mappers;
import lombok.NonNull;
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
 * Сервисный класс для управления блюдами.
 * Этот класс используется для управления блюдами приложения.
 * @author szabolotnyi
 * @version 1.0.0
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
    private final Mappers mappers;
    private MessageHelper helper;

    /**
     * Сервис управления блюдами (конструктор).
     *
     * @param dishRepository     Репозиторий блюд.
     * @param categoryRepository Репозиторий категорий.
     * @param mappers            Мапперы для преобразования данных.
     * @param helper             Вспомогательный класс для работы с сообщениями.
     */
    public DishService(DishRepository dishRepository, CategoryRepository categoryRepository, Mappers mappers, MessageHelper helper) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
        this.mappers = mappers;
        this.helper = helper;
    }

    /**
     * Получает пагинированный список блюд вместе с информацией о категории.
     *
     * @param pageNum       Номер страницы для получения.
     * @param sortField     Поле для сортировки.
     * @param sortDirection Направление сортировки (ASC или DESC).
     * @param categoryId    ID категории для фильтрации (по умолчанию, если null или отрицательный).
     * @return Объект PageDishesDTO, содержащий пагинированный список блюд, информацию о категориях,
     * текущую страницу, общее количество страниц, поле сортировки, направление сортировки и ID категории.
     */
    @Transactional
    public PageDishesDTO findAllDishesPage(Integer pageNum, String sortField,
                                           String sortDirection, int categoryId) {
        categoryId = categoryId == 0 || categoryId < 0 ? categoryIdDefault : categoryId;
        Sort sort = validationSetDefault(sortField, sortDirection);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        Page<Dish> page = (categoryId == 0)
                ? dishRepository.findAll(pageable)
                : dishRepository.findByCategoryId(categoryId, pageable);

        List<Category> categories = categoryRepository.findAll();

        return PageDishesDTO.builder()
                .dishes(mappers.dishesToDishDTOs(page.getContent()))
                .categories(mappers.categoriesToCategoryDTOs(categories))
                .currentPage(pageNum)
                .totalPages(page.getTotalPages())
                .sortField(sortField)
                .sortDirection(sortDirection)
                .categoryId(categoryId)
                .build();
    }

    /**
     * Проверяет и устанавливает значения по умолчанию для параметров сортировки.
     *
     * @param sortField     Поле для сортировки.
     * @param sortDirection Направление сортировки (ASC или DESC).
     * @return Объект Sort с проверенными и значениями по умолчанию для сортировки.
     */
    public Sort validationSetDefault(String sortField, String sortDirection) {
        sortField = sortField == null || sortField.isEmpty() || sortField.equals("null") ? sortDefault
                : (sortField.equals("name")) ? helper.getMessage("db.name") : sortField;

        sortDirection = (sortDirection != null && !sortDirection.isEmpty() && (sortDirection.equalsIgnoreCase("asc")
                || sortDirection.equalsIgnoreCase("desc"))) ? sortDirection : sortDirectionDefault;

        return sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }

    /**
     * Получает список всех блюд, отсортированных по ID в порядке возрастания.
     *
     * @return Список объектов DishDTO, представляющих блюда.
     */
    public List<DishDTO> findAllDishes() {
        Sort sort = Sort.by("id").ascending();
        return mappers.dishesToDishDTOs(dishRepository.findAll(sort));
    }

    /**
     * Находит блюдо по его ID.
     *
     * @param id ID блюда, которое нужно получить.
     * @return Объект Dish с указанным ID.
     * @throws NoSuchElementException если блюдо с указанным ID не существует.
     */
    public Dish findById(int id) {
        return dishRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(helper.getLogMessage("select.dish.not")));
    }

    /**
     * Сохраняет новое блюдо в базу данных.
     *
     * @param dish Объект Dish, который нужно сохранить.
     * @return Сохраненный объект Dish.
     * @throws NoSuchElementException если указанный ID категории не существует.
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
     * Обновляет существующее блюдо в базе данных.
     *
     * @param dish Объект Dish, который нужно обновить.
     */
    public void update(@NonNull Dish dish) {
        dish.setTime(LocalDateTime.now());
        dishRepository.save(dish);
    }

    /**
     * Удаляет блюдо по его ID.
     *
     * @param id ID блюда, которое нужно удалить.
     */
    public void delete(@NonNull int id) {
        dishRepository.deleteById(id);
    }
}
