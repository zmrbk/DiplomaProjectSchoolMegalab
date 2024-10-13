package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.CharterService;
import kg.megacom.diplomaprojectschoolmegalab.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/charters")
@RequiredArgsConstructor
public class CharterController {

    private final CharterService charterService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CharterDto charterDto) throws AccountNotFoundException {
        try {
            charterService.create(charterDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CharterDto>> update(@RequestBody CharterDto charterDto, @PathVariable Long id) throws AccountNotFoundException {
        return ResponseEntity.ok(charterService.update(charterDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(charterService.delete(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<CharterDto>>> getAll() {
        return ResponseEntity.ok(charterService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CharterDto>> getById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(charterService.getById(id));
    }
}
