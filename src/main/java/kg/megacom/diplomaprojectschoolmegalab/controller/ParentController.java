package kg.megacom.diplomaprojectschoolmegalab.controller;


import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.ParentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/parents")
@Slf4j
public class ParentController {

    private final ParentServiceImpl parentService;

    @PostMapping
    public ResponseEntity<Response<ParentDto>> create(@RequestBody ParentDto parentDto) {
        log.info("[#createParent] is calling");
        try {
            parentService.create(parentDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Parent is created", parentDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Invalid input", null));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ParentDto>> getById(@PathVariable Long id) {
        log.info("[#getParentById] is calling");
        Response<ParentDto> response = parentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<ParentDto>>> getAll() {
        log.info("[#getAll] is calling");
        Response<List<ParentDto>> response = parentService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ParentDto>> update(@RequestBody ParentDto parentDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response<ParentDto> response = parentService.update(parentDto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        parentService.delete(id);
        return ResponseEntity.ok(new Response<>("Deleted!", "ID: " + id));
    }
}
