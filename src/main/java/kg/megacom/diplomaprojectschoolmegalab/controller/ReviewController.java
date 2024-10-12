package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReviewDto reviewDto) throws AccountNotFoundException {
        reviewService.create(reviewDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ReviewDto>> update(@RequestBody ReviewDto reviewDto, @PathVariable Long id) throws AccountNotFoundException {
        return ResponseEntity.ok(reviewService.update(reviewDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(reviewService.delete(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<ReviewDto>>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ReviewDto>> getById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(reviewService.getById(id));
    }
}
