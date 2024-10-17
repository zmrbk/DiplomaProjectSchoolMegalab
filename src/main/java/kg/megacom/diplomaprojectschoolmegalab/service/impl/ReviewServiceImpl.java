package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Review;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.ReviewMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.ReviewRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.UserRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.ReviewService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Реализация сервиса для работы с отзывами.
 *
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения отзывов.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    /**
     * Создание нового отзыва.
     *
     * @param reviewDto объект данных отзыва, который нужно создать.
     * @return
     * @throws AccountNotFoundException если студент или автор отзыва не найдены.
     */
    @Override
    public ReviewDto create(ReviewDto reviewDto) throws AccountNotFoundException {
        Review review = new Review();
        review.setReview(reviewDto.getReview());
        review.setStudent(studentRepository.findById(reviewDto.getStudentId())
                .orElseThrow(() -> new AccountNotFoundException("Student not found")));
        review.setAuthor(userRepository.findById(reviewDto.getAuthorId())
                .orElseThrow(() -> new AccountNotFoundException("Author not found")));
        review.setCreationDate(LocalDateTime.now());
        reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    /**
     * Обновление существующего отзыва.
     *
     * @param reviewDto объект данных отзыва с обновленной информацией.
     * @param id        идентификатор отзыва, который нужно обновить.
     * @throws AccountNotFoundException если студент или автор отзыва не найдены.
     * @throws EntityNotFoundException   если отзыв не найден.
     * @return ответ с обновленным объектом отзыва.
     */
    @Override
    public Response<ReviewDto> update(ReviewDto reviewDto, Long id) throws AccountNotFoundException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        review.setReview(reviewDto.getReview());
        review.setStudent(studentRepository.findById(reviewDto.getStudentId())
                .orElseThrow(() -> new AccountNotFoundException("Student not found")));
        review.setAuthor(userRepository.findById(reviewDto.getAuthorId())
                .orElseThrow(() -> new AccountNotFoundException("Author not found")));

        reviewRepository.save(review);
        return new Response<>("Review updated successfully", reviewMapper.toDto(review));
    }

    /**
     * Удаление отзыва по его идентификатору.
     *
     * @param id идентификатор отзыва, который нужно удалить.
     * @throws EntityNotFoundException если отзыв не найден.
     * @return ответ о результате операции удаления.
     */
    @Override
    public Response<String> delete(Long id) throws EntityNotFoundException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        reviewRepository.delete(review);
        return new Response<>("Review deleted successfully", null);
    }

    /**
     * Получение всех отзывов.
     *
     * @return список всех отзывов.
     */
    @Override
    public Response<List<ReviewDto>> getAll() {
        List<ReviewDto> reviews = reviewRepository.findAll()
                .stream().map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return new Response<>("All reviews retrieved successfully", reviews);
    }

    /**
     * Получение отзыва по его идентификатору.
     *
     * @param id идентификатор отзыва, который нужно получить.
     * @throws EntityNotFoundException если отзыв не найден.
     * @return ответ с найденным объектом отзыва.
     */
    @Override
    public Response<ReviewDto> getById(Long id) throws EntityNotFoundException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        return new Response<>("Review retrieved successfully", reviewMapper.toDto(review));
    }

    @Override
    public List<ReviewDto> getReviewsForStudent(Long studentId) {
        // Получаем список отзывов для студента
        List<Review> reviews = reviewRepository.findByStudentId(studentId);

        // Преобразуем сущности Review в ReviewDto и возвращаем список
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewsByCaptain(Long captainId) {
        // Получаем список отзывов, созданных старостой
        List<Review> reviews = reviewRepository.findByAuthorId(captainId);

        // Преобразуем сущности в DTO и возвращаем список
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }
}