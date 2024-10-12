package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.MarkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.MarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping(value = "/marks")
@RequiredArgsConstructor
@Slf4j
public class MarkController {

    private final MarkService markService;

    @PostMapping
    public ResponseEntity<Response<MarkDto>> create(@RequestBody MarkDto markDto) {
        log.info("[#createMark] is calling");
        try {
            markService.create(markDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Mark is created successfully", markDto));
        } catch (IllegalArgumentException e) {
            log.error("Error creating mark: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new Response<>("Invalid input", null));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<MarkDto>> getById(@PathVariable Long id) {
        log.info("[#getMarkById] is calling");
        MarkDto markDto = markService.getById(id);
        return ResponseEntity.ok(new Response<>("Mark retrieved successfully", markDto));
    }

    @GetMapping
    public ResponseEntity<Response<List<MarkDto>>> getAll() {
        log.info("[#getAllMarks] is calling");
        Response<List<MarkDto>> response = markService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<MarkDto>> update(@PathVariable Long id, @RequestBody MarkDto markDto) {
        log.info("[#updateMark] is calling");
        Response<MarkDto> response = markService.update(id, markDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#deleteMark] is calling");
        markService.delete(id);
        return ResponseEntity.ok(new Response<>("Mark deleted successfully", "ID: " + id));
    }
}
