package de.telran.pizza.controller;

import de.telran.pizza.MockData;
import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Review;
import de.telran.pizza.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса ReviewController.
 */
class ReviewControllerTest {
    private ReviewService reviewService;
    private MessageHelper helper;
    private ReviewController reviewController;

    /**
     * Общая настройка для всех тестов.
     */
    @BeforeEach
    void setUp() {
        reviewService = mock(ReviewService.class);
        helper = mock(MessageHelper.class);
        reviewController = new ReviewController(reviewService, helper);
    }

    /**
     * Тестирование метода getAllReviews в ReviewController.
     * Проверяет, что метод возвращает ожидаемый список отзывов и корректный статус ответа.
     */
    @Test
    void testGetAllReviews() {
        // Готовим Mock данные
        List<Review> expectedReviews = MockData.getMockedListOfReviews();

        // Устанавливаем поведение mock-объекта
        when(reviewService.getAllReviews()).thenReturn(expectedReviews);

        // Шаг 3: Вызываем тестируемый метод
        ResponseEntity<List<Review>> response = reviewController.getAllReviews();

        // Проверяем ожидаемый результат
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReviews, response.getBody());
    }

    /**
     * Тестирование метода createReview в ReviewController.
     * Проверяет, что метод создает новый отзыв с корректным статусом ответа и возвращает созданный отзыв.
     */
    @Test
    void testCreateReview() {
        // Готовим Mock данные
        Review reviewToCreate = MockData.getMockedReview();

        // Устанавливаем поведение mock-объекта
        when(reviewService.createReview(reviewToCreate)).thenReturn(reviewToCreate);

        // Шаг 3: Вызываем тестируемый метод
        ResponseEntity<Review> response = reviewController.createReview(reviewToCreate);

        // Проверяем ожидаемый результат
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewToCreate, response.getBody());
    }

    /**
     * Тестирование метода createReview в ReviewController с генерацией исключения.
     * Проверяет, что метод выбрасывает ожидаемое исключение при ошибке создания отзыва.
     */
    @Test
    void testCreateReviewException() {
        // Готовим Mock данные
        Review reviewToCreate = MockData.getMockedReview();

        // Устанавливаем поведение mock-объекта
        when(reviewService.createReview(reviewToCreate)).thenThrow(new RuntimeException("Error"));

        // Проверяем, что метод выбрасывает ожидаемое исключение
        assertThrows(ResponseStatusException.class, () -> reviewController.createReview(reviewToCreate));
    }

    /**
     * Тестирование метода getReviewById в ReviewController.
     * Проверяет, что метод возвращает ожидаемый отзыв с корректным статусом ответа.
     */
    @Test
    void testGetReviewById() {
        // Готовим Mock данные
        int reviewId = 1;
        Review expectedReview = MockData.getMockedReview();

        // Устанавливаем поведение mock-объекта
        when(reviewService.findById(reviewId)).thenReturn(expectedReview);

        // Шаг 3: Вызываем тестируемый метод
        ResponseEntity<Review> response = reviewController.getReviewById(reviewId);

        // Проверяем ожидаемый результат
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReview, response.getBody());
    }

    /**
     * Тестирование метода getReviewById в ReviewController с генерацией исключения.
     * Проверяет, что метод выбрасывает ожидаемое исключение при ошибке получения отзыва по ID.
     */
    @Test
    void testGetReviewByIdException() {
        // Готовим Mock данные
        int reviewId = 1;

        // Устанавливаем поведение mock-объекта
        when(reviewService.findById(reviewId)).thenThrow(new RuntimeException("Error"));

        // Проверяем, что метод выбрасывает ожидаемое исключение
        assertThrows(ResponseStatusException.class, () -> reviewController.getReviewById(reviewId));
    }

    /**
     * Тестирование метода update в ReviewController.
     * Проверяет, что метод обновляет отзыв с корректным статусом ответа.
     */
    @Test
    void testUpdate() {
        // Готовим Mock данные
        Review reviewToUpdate = MockData.getMockedReview();

        // Шаг 3: Вызываем тестируемый метод
        assertDoesNotThrow(() -> reviewController.update(reviewToUpdate));

        // Проверяем, что метод вызывает соответствующий метод сервиса
        verify(reviewService).updateReview(reviewToUpdate);
    }

    /**
     * Тестирование метода update в ReviewController с генерацией исключения.
     * Проверяет, что метод выбрасывает ожидаемое исключение при ошибке обновления отзыва.
     */
    @Test
    void testUpdateException() {
        // Готовим Mock данные
        Review reviewToUpdate = MockData.getMockedReview();

        // Устанавливаем поведение mock-объекта
        doThrow(new RuntimeException("Error")).when(reviewService).updateReview(reviewToUpdate);

        // Проверяем, что метод выбрасывает ожидаемое исключение
        assertThrows(ResponseStatusException.class, () -> reviewController.update(reviewToUpdate));
    }

    /**
     * Тестирование метода deleteReview в ReviewController.
     * Проверяет, что метод удаляет отзыв с корректным статусом ответа.
     */
    @Test
    void testDeleteReview() {
        // Готовим Mock данные
        int reviewId = 1;

        // Шаг 3: Вызываем тестируемый метод
        assertDoesNotThrow(() -> reviewController.deleteReview(reviewId));

        // Проверяем, что метод вызывает соответствующий метод сервиса
        verify(reviewService).deleteReview(reviewId);
    }

    /**
     * Тестирование метода deleteReview в ReviewController с генерацией исключения.
     * Проверяет, что метод выбрасывает ожидаемое исключение при ошибке удаления отзыва.
     */
    @Test
    void testDeleteReviewException() {
        // Готовим Mock данные
        int reviewId = 1;

        // Устанавливаем поведение mock-объекта
        doThrow(new RuntimeException("Error")).when(reviewService).deleteReview(reviewId);

        // Проверяем, что метод выбрасывает ожидаемое исключение
        assertThrows(ResponseStatusException.class, () -> reviewController.deleteReview(reviewId));
    }

    /**
     * Тестирование метода getReviewsCount в ReviewController.
     * Проверяет, что метод возвращает ожидаемое количество отзывов с корректным статусом ответа.
     */
    @Test
    void testGetReviewsCount() {
        // Готовим Mock данные
        List<Review> reviewList = MockData.getMockedListOfReviews();

        // Устанавливаем поведение mock-объекта
        when(reviewService.getAllReviews()).thenReturn(reviewList);

        // Шаг 3: Вызываем тестируемый метод
        ResponseEntity<Integer> response = reviewController.getReviewsCount();

        // Проверяем ожидаемый результат
        assertEquals(reviewList.size(), response.getBody());
    }

    /**
     * Тестирование метода getAverageRating в ReviewController.
     * Проверяет, что метод возвращает ожидаемую среднюю оценку с корректным статусом ответа.
     */
    @Test
    void testGetAverageRating() {
        // Готовим Mock данные
        double expectedAverageRating = 4.5;

        // Устанавливаем поведение mock-объекта
        when(reviewService.getAverageReviewRating()).thenReturn(expectedAverageRating);

        // Шаг 3: Вызываем тестируемый метод
        ResponseEntity<Double> response = reviewController.getAverageRating();

        // Проверяем ожидаемый результат
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAverageRating, response.getBody());
    }
}