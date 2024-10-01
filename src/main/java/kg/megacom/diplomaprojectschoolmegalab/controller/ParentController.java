package kg.megacom.diplomaprojectschoolmegalab.controller;


import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.ParentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/parents")
@Slf4j
public class ParentController {

    private final ParentServiceImpl parentService;

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody ParentDto parentDto) {
        log.info("[#createParent] is calling");
        try {
            parentService.create(parentDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response("Grade is created", parentDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response("Invalid input", e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        log.info("[#getParentById] is calling");
        Response response = parentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        log.info("[#getAll] is calling");
        Response response = parentService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> update(@RequestBody ParentDto parentDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response response = parentService.update(parentDto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        parentService.delete(id);
        return ResponseEntity.ok(new Response("Deleted!", "ID: " + id));
    }
}
