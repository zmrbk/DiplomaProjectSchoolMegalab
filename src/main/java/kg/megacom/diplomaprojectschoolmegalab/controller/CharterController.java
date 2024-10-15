package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.CharterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

/**
 * CharterController - это REST-контроллер, который управляет процессами
 * создания, обновления, удаления и получения уставов в системе.
 * Он предоставляет конечные точки для работы с уставами.
 *
 * <p>Контроллер использует {@link CharterService} для выполнения
 * бизнес-логики, связанной с уставами.</p>
 */
@RestController
@RequestMapping("/charters")
@RequiredArgsConstructor
public class CharterController {

    private final CharterService charterService;

    /**
     * Создание нового устава.
     *
     * @param charterDto объект запроса, содержащий данные для создания устава
     * @return ResponseEntity с кодом 200 (OK), если устав успешно создан,
     *         или сообщение об ошибке с кодом 404 (NOT FOUND), если не найдена соответствующая учетная запись
     * @throws AccountNotFoundException если учетная запись не найдена
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody CharterDto charterDto) throws AccountNotFoundException {
        try {
            charterService.create(charterDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Обновление существующего устава.
     *
     * @param charterDto объект запроса, содержащий обновленные данные устава
     * @param id идентификатор чарта, который необходимо обновить
     * @return ResponseEntity с объектом Response, содержащим обновленный устав
     * @throws AccountNotFoundException если учетная запись не найдена
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<CharterDto>> update(@RequestBody CharterDto charterDto,
                                                       @PathVariable Long id) throws AccountNotFoundException {
        return ResponseEntity.ok(charterService.update(charterDto, id));
    }

    /**
     * Удаление устава по идентификатору.
     *
     * @param id идентификатор устава, который необходимо удалить
     * @return ResponseEntity с объектом Response, содержащим сообщение о результате удаления
     * @throws EntityNotFoundException если устав с указанным идентификатором не найден
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(charterService.delete(id));
    }

    /**
     * Получение списка всех уставов.
     *
     * @return ResponseEntity с объектом Response, содержащим список всех уставов
     */
    @GetMapping
    public ResponseEntity<Response<List<CharterDto>>> getAll() {
        return ResponseEntity.ok(charterService.getAll());
    }

    /**
     * Получение устава по идентификатору.
     *
     * @param id идентификатор устава, который необходимо получить
     * @return ResponseEntity с объектом Response, содержащим найденный устав
     * @throws EntityNotFoundException если устав с указанным идентификатором не найден
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<CharterDto>> getById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(charterService.getById(id));
    }
}
