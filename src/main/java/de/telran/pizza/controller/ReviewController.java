package de.telran.pizza.controller;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Review;
import de.telran.pizza.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для управления отзывами пиццерии.
 */
@RestController
@Slf4j
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final MessageHelper helper;

    /**
     * Контроллер отзывов (конструктор).
     *
     * @param reviewService Сервис управления отзывами.
     * @param helper      Вспомогательный класс для работы с сообщениями.
     */
    public ReviewController(ReviewService reviewService, MessageHelper helper) {
        this.reviewService = reviewService;
        this.helper = helper;
    }

    /**
     * Получить все отзывы.
     *
     * @return Список всех отзывов.
     */
    @GetMapping("/getAll")
    @Operation(
            summary = "Получение всех отзывов пиццерии",
            description = "Получение всех отзывов Пиццерии, хранящихся в БД")
    public ResponseEntity<List<Review>> getAllReviews() {
        log.info(helper.getLogMessage("all.reviews.log"));
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    /**
     * Создать новый отзыв.
     *
     * @return ResponseEntity, содержащий список всех отзывов.
     */
    @PostMapping("/create")
    @Operation(
            summary = "Создание новых отзывов",
            description = "Создание новых отзывов пиццерии")
    public ResponseEntity<Review> createReview(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody
            (description = "Найменование отзывов") Review review) {
        log.info(helper.getLogMessage("create.review.log"));
        try {
            return ResponseEntity.ok(reviewService.createReview(review));
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    helper.getLogMessage("create.review.not") + review.getId());
        }
    }

    /**
     * Получает отзыв по его ID.
     *
     * @param id ID отзыва, который нужно получить.
     * @return ResponseEntity, содержащий сущность Review с указанным ID.
     */
    @GetMapping("/update")
    @Operation(
            summary = "Получение отзыва по ID",
            description = "Получение отзыва Пиццерии по ID, хранящихся в БД")
    public ResponseEntity<Review> getReviewById(@Valid @RequestParam(value = "id")
                                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                            description = "Идентефикатор блюда") int id) {
        log.info(helper.getLogMessage("select.review.log") + id);
        try {
            return ResponseEntity.ok(reviewService.findById(id));
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, helper.getLogMessage("select.review.not") + id);
        }
    }

    /**
     * Обновляет существующее отзыва на основе предоставленной сущности Review.
     *
     * @param review Сущность Review, которая будет обновлена.
     */
    @PutMapping("/update")
    @Operation(
            summary = "Изменить отзыв",
            description = "Изменение отзыва пиццерии, хранящихся в БД")
    public void update(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Найменование блюд") Review review) {
        log.info(helper.getLogMessage("update.review.log") + review.getId());
        try {
            reviewService.updateReview(review);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    helper.getLogMessage("update.review.not"));
        }
    }

    /**
     * Удалить отзыв по идентификатору.
     *
     * @param id Идентификатор отзыва для удаления.
     */
    @DeleteMapping("/delete")
    @Operation(
            summary = "Удалить отзыв",
            description = "Удаление отзыва пиццерии, хранящихся в БД")
    public void deleteReview(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Идентификатор отзыва") int id) {
        log.info(helper.getLogMessage("delete.review.log") + id);
        try {
            reviewService.deleteReview(id);
        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    helper.getLogMessage("delete.review.not"));
        }
    }

    /**
     * Получает количество отзывов.
     * @return ResponseEntity, содержащий количество отзывов.
     */
    @Operation(summary = "Получение количества всех отзывов пиццерии",
            description = "Получение количества всех отзывов Пиццерии, хранящихся в БД")
    @ApiResponse(responseCode = "200", description = "Успешно получено количество отзывов.")
    @GetMapping("/count")
    public ResponseEntity<Integer> getReviewsCount() {
        log.info(helper.getLogMessage("all.reviews.count.log"));
        return ResponseEntity.ok(reviewService.getAllReviews().size());
    }

    /**
     * Получение средней оценки всех отзывов.
     *
     * Этот метод позволяет получить среднюю оценку всех отзывов в пиццерии.
     *
     * @return ResponseEntity с средней оценкой всех отзывов.
     */
    @GetMapping("/average")
    @Operation(summary = "Получаем среднюю оценку всех отзывов",
            description = "Получаем среднюю оценку всех отзывов, пиццерии.")
    public ResponseEntity<Double> getAverageRating() {
        log.info(helper.getLogMessage("get.average.review.rating"));
        return ResponseEntity.ok(reviewService.getAverageReviewRating());
    }
}
