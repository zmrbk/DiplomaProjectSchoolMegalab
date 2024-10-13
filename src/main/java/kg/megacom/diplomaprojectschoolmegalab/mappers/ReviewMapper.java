package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Review;
import org.springframework.stereotype.Component;

/**
 * Mapper для преобразования между сущностью {@link Review} и DTO {@link ReviewDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объекта {@link Review} в {@link ReviewDto}.
 * </p>
 */
@Component
public class ReviewMapper {

    /**
     * Преобразует объект {@link Review} в объект {@link ReviewDto}.
     *
     * @param review Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link ReviewDto}.
     */
    public ReviewDto toDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setReview(review.getReview());
        dto.setStudentId(review.getStudent().getId());
        dto.setAuthorId(review.getAuthor().getId());
        dto.setCreationDate(review.getCreationDate());
        return dto;
    }
}
