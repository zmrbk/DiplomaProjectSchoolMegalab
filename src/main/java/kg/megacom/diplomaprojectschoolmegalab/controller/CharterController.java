package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.CharterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Charter", description = "APIs for managing Charters")
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
    @Operation(summary = "Create a new charter", description = "Create a new charter with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Charter created successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
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
    @Operation(summary = "Update an existing charter", description = "Update a charter with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Charter updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Charter or account not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'SECRETARY')")
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
    @Operation(summary = "Delete a charter", description = "Delete a charter by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Charter deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Charter not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(charterService.delete(id));
    }

    /**
     * Получение списка всех уставов.
     *
     * @return ResponseEntity с объектом Response, содержащим список всех уставов
     */
    @Operation(summary = "Get all charters", description = "Retrieve a list of all charters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of charters retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)))
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'SECRETARY')")
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
    @Operation(summary = "Get a charter by ID", description = "Retrieve a charter by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Charter retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Charter not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'SECRETARY')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<CharterDto>> getById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(charterService.getById(id));
    }
}
