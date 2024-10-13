package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

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