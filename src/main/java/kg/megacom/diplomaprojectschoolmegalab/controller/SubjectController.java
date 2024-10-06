package kg.megacom.diplomaprojectschoolmegalab.controller;


import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.SubjectsDto;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.ParentServiceImpl;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/subjects")
@Slf4j
public class SubjectController {

    private final SubjectServiceImpl subjectService;

    @PostMapping
    public ResponseEntity<Response<SubjectsDto>> create(@RequestBody SubjectsDto subjectsDto) {
        log.info("[#create] is calling");
        try {
            subjectService.create(subjectsDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Subject is created", subjectsDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Invalid input", null));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<SubjectsDto>> getById(@PathVariable Long id) {
        log.info("[#getById] is calling");
        Response<SubjectsDto> response = subjectService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<SubjectsDto>>> getAll() {
        log.info("[#getAll] is calling");
        Response<List<SubjectsDto>> response = subjectService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<SubjectsDto>> update(@RequestBody SubjectsDto subjectsDto, @PathVariable Long id) {
        log.info("[#update] is calling");
        Response<SubjectsDto> response = subjectService.update(subjectsDto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        subjectService.delete(id);
        return ResponseEntity.ok(new Response<>("Deleted!", "ID: " + id));
    }
}
