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

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public void create(ReviewDto reviewDto) throws AccountNotFoundException {
        Review review = new Review();
        review.setReview(reviewDto.getReview());
        review.setStudent(studentRepository.findById(reviewDto.getStudentId())
                .orElseThrow(() -> new AccountNotFoundException("Student not found")));
        review.setAuthor(userRepository.findById(reviewDto.getAuthorId())
                .orElseThrow(() -> new AccountNotFoundException("Author not found")));
        review.setCreationDate(LocalDateTime.now());
        reviewRepository.save(review);
    }

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
        return new Response<>("Review updated successfully",reviewMapper.toDto(review));
    }

    @Override
    public Response<String> delete(Long id) throws EntityNotFoundException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        reviewRepository.delete(review);
        return new Response<>("Review deleted successfully", null);
    }

    @Override
    public Response<List<ReviewDto>> getAll() {
        List<ReviewDto> reviews = reviewRepository.findAll()
                .stream().map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return new Response<>("All reviews retrieved successfully",reviews);
    }

    @Override
    public Response<ReviewDto> getById(Long id) throws EntityNotFoundException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        return new Response<>("Review retrieved successfully",reviewMapper.toDto(review));
    }
}
