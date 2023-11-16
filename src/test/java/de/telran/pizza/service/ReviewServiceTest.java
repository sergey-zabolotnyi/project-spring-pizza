package de.telran.pizza.service;

import de.telran.pizza.MockData;
import de.telran.pizza.domain.entity.Review;
import de.telran.pizza.domain.entity.User;
import de.telran.pizza.repository.ReviewRepository;
import de.telran.pizza.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    private static final int EXISTING_REVIEW_ID = 1;
    private static final int NON_EXISTING_REVIEW_ID = 99;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Тестирование метода getAllReviews в ReviewService.
     * Проверяет, что метод возвращает ожидаемый список отзывов.
     */
    @Test
    void testGetAllReviews() {
        // Подготовка мокованных данных
        List<Review> expectedReviews = MockData.getMockedListOfReviews();
        when(reviewRepository.findAll()).thenReturn(expectedReviews);

        // Вызов тестируемого метода
        List<Review> actualReviews = reviewService.getAllReviews();

        // Проверка результатов
        assertEquals(expectedReviews, actualReviews);
    }

    /**
     * Тестирование метода createReview в ReviewService.
     * Проверяет, что метод создает новый отзыв с корректным временем и вызывает соответствующие методы репозитория.
     */
    @Test
    void testCreateReview() {
        // Подготовка мокованных данных
        Review reviewToCreate = MockData.getMockedReview();
        User user = MockData.getMockedUser();

        when(userRepository.findById(reviewToCreate.getUser().getId())).thenReturn(Optional.of(user));
        when(reviewRepository.save(reviewToCreate)).thenReturn(reviewToCreate);

        // Вызов тестируемого метода
        Review createdReview = reviewService.createReview(reviewToCreate);

        // Проверка результатов
        assertEquals(reviewToCreate, createdReview);
        assertNotNull(createdReview.getTime());
        verify(userRepository).findById(reviewToCreate.getUser().getId());
        verify(reviewRepository).save(reviewToCreate);
    }

    /**
     * Тестирование метода findById в ReviewService с существующим отзывом.
     * Проверяет, что метод возвращает ожидаемый отзыв.
     */
    @Test
    void testFindByIdExistingReview() {
        // Подготовка мокованных данных
        Review existingReview = MockData.getMockedReview();
        when(reviewRepository.findById(EXISTING_REVIEW_ID)).thenReturn(Optional.of(existingReview));

        // Вызов тестируемого метода
        Review foundReview = reviewService.findById(EXISTING_REVIEW_ID);

        // Проверка результатов
        assertEquals(existingReview, foundReview);
    }

    /**
     * Тестирование метода findById в ReviewService с несуществующим отзывом.
     * Проверяет, что метод выбрасывает ожидаемое исключение NullPointerException.
     */
    @Test
    void testFindByIdNonExistingReview() {
        // Подготовка мокованных данных
        when(reviewRepository.findById(NON_EXISTING_REVIEW_ID)).thenReturn(Optional.empty());

        // Проверка, что вызов метода выбрасывает ожидаемое исключение
        assertThrows(NullPointerException.class, () -> reviewService.findById(NON_EXISTING_REVIEW_ID));
    }

    /**
     * Тестирование метода updateReview в ReviewService.
     * Проверяет, что метод обновляет отзыв с корректным временем и вызывает соответствующий метод репозитория.
     */
    @Test
    void testUpdateReview() {
        // Подготовка мокованных данных
        Review updatedReview = MockData.getMockedReview();

        // Вызов тестируемого метода
        reviewService.updateReview(updatedReview);

        // Проверка результатов
        assertNotNull(updatedReview.getTime());
        verify(reviewRepository).save(updatedReview);
    }

    /**
     * Тестирование метода deleteReview в ReviewService.
     * Проверяет, что метод удаляет отзыв и вызывает соответствующий метод репозитория без выброса исключения.
     */
    @Test
    void testDeleteReview() {
        // Подготовка мокованных данных
        doNothing().when(reviewRepository).deleteById(EXISTING_REVIEW_ID);

        // Проверка, что вызов метода не выбрасывает исключение
        assertDoesNotThrow(() -> reviewService.deleteReview(EXISTING_REVIEW_ID));

        // Проверка, что метод репозитория был вызван с ожидаемым аргументом
        verify(reviewRepository).deleteById(EXISTING_REVIEW_ID);
    }

    /**
     * Тестирование метода getAverageReviewRating в ReviewService.
     * Проверяет, что метод возвращает ожидаемую среднюю оценку отзывов.
     */
    @Test
    void testGetAverageReviewRating() {
        // Подготовка мокованных данных
        double expectedAverageRating = 4.5;
        when(reviewRepository.findAverageReviewRating()).thenReturn(expectedAverageRating);

        // Вызов тестируемого метода
        Double actualAverageRating = reviewService.getAverageReviewRating();

        // Проверка результатов
        assertEquals(expectedAverageRating, actualAverageRating);
    }
}