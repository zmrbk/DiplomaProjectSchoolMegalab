package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.ParentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления родителями.
 * Предоставляет RESTful API для создания, получения, обновления и удаления родителей.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/parents")
@Slf4j
public class ParentController {

    private final ParentServiceImpl parentService;

    /**
     * Создает нового родителя.
     *
     * @param parentDto DTO с данными родителя.
     * @return ResponseEntity с информацией о созданном родителе.
     */
    @PostMapping
    public ResponseEntity<Response<ParentDto>> create(@RequestBody ParentDto parentDto) {
        log.info("[#createParent] is calling");
        try {
            parentService.create(parentDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Parent is created successfully", parentDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает родителя по его ID.
     *
     * @param id ID родителя.
     * @return ResponseEntity с данными родителя.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ParentDto>> getById(@PathVariable Long id) {
        log.info("[#getParentById] is calling");
        Response<ParentDto> response = parentService.getParentDtoById(id); // Вызов нового метода
        return ResponseEntity.ok(response);
    }

    /**
     * Получает список всех родителей.
     *
     * @return ResponseEntity со списком родителей.
     */
    @GetMapping
    public ResponseEntity<Response<List<ParentDto>>> getAll() {
        log.info("[#getAll] is calling");
        Response<List<ParentDto>> response = parentService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет информацию о родителе по его ID.
     *
     * @param parentDto DTO с новыми данными родителя.
     * @param id ID родителя, которого нужно обновить.
     * @return ResponseEntity с обновленной информацией о родителе.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ParentDto>> update(@RequestBody ParentDto parentDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response<ParentDto> response = parentService.update(parentDto, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет родителя по его ID.
     *
     * @param id ID родителя, которого нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        parentService.delete(id);
        return ResponseEntity.ok(new Response<>("Parent deleted successfully!", "ID: " + id));
    }
}
