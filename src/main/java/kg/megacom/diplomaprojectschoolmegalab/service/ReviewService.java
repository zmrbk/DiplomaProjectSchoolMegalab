package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface ReviewService {
    ReviewDto create(ReviewDto reviewDto) throws AccountNotFoundException;
    Response<ReviewDto> update(ReviewDto reviewDto, Long id) throws AccountNotFoundException;
    Response<String> delete(Long id) throws EntityNotFoundException;
    Response<List<ReviewDto>> getAll();
    Response<ReviewDto> getById(Long id) throws EntityNotFoundException;

    List<ReviewDto> getReviewsForStudent(Long id);
    List<ReviewDto> getReviewsByCaptain(Long captainId);

}