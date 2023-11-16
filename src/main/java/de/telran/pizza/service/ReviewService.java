package de.telran.pizza.service;

import de.telran.pizza.config.MessageHelper;
import de.telran.pizza.domain.entity.Review;
import de.telran.pizza.repository.ReviewRepository;
import de.telran.pizza.repository.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс-сервис для управления отзывами.
 * Этот класс используется для управления отзывами приложения.
 * @author szabolotnyi
 * @version 1.0.0
 */
@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MessageHelper helper;

    /**
     * Сервис управления отзывами (конструктор).
     *
     * @param reviewRepository Репозиторий отзывов.
     * @param userRepository
     * @param helper           Вспомогательный класс для работы с сообщениями.
     */
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, MessageHelper helper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.helper = helper;
    }

    /**
     * Получить все отзывы.
     *
     * @return Список всех отзывов.
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Создать новый отзыв.
     *
     * @param review Отзыв для создания.
     * @return Созданный отзыв.
     */
    @Transactional
    public Review createReview(@NonNull Review review) {
        review.setTime(LocalDateTime.now());
        review.setUser(userRepository.findById(review.getUser().getId()).orElseThrow(
                () -> new NoSuchElementException(helper.getLogMessage("find.review.not"))
        ));
        return reviewRepository.save(review);
    }

    /**
     * Находит отзыв по его ID.
     *
     * @param id ID отзыва, который нужно получить.
     * @return Объект Review с указанным ID.
     * @throws NoSuchElementException если отзыв с указанным ID не существует.
     */
    public Review findById(int id) {
        return reviewRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(helper.getLogMessage("select.review.not")));
    }

    /**
     * Обновляет существующий отзыв.
     *
     * @param updatedReview Обновленный отзыв.
     * @return Обновленный отзыв, если найден, в противном случае - null.
     */
    public void updateReview(@NonNull Review updatedReview) {
        updatedReview.setTime(LocalDateTime.now());
        reviewRepository.save(updatedReview);
    }

    /**
     * Удаляет отзыв по идентификатору.
     *
     * @param reviewId ID блюда, которое нужно удалить.
     */
    public void deleteReview(@NonNull int reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    /**
     * Получает среднюю оценку отзывов.
     *
     * Этот метод вычисляет и возвращает среднюю оценку всех отзывов в репозитории.
     *
     * @return Объект типа Double, представляющий среднюю оценку отзывов.
     */
    public Double getAverageReviewRating() {
        return reviewRepository.findAverageReviewRating();
    }
}
