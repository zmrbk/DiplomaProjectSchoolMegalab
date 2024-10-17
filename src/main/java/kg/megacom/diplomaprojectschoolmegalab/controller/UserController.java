package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.dto.*;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления пользователями.
 * Предоставляет RESTful API для получения, обновления и удаления пользователей, а также для установки ролей.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Slf4j
@Tag(name = "User", description = "APIs for managing users")
public class UserController {

    private final UserServiceImpl userService;

    /**
     * Получает пользователя по его ID.
     *
     * @param id ID пользователя, которого нужно получить.
     * @return ResponseEntity с найденным пользователем.
     */
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> getById(@PathVariable Long id) {
        log.info("[#getUserById] is calling");

        try {
            Response<UserDto> response = userService.getUserResponseById(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
    }

    /**
     * Получает список всех пользователей с поддержкой пагинации и сортировки.
     *
     * @param page номер страницы (по умолчанию 0).
     * @param size размер страницы (по умолчанию 10).
     * @param sort параметры сортировки (по умолчанию "username,asc").
     * @return ResponseEntity со списком пользователей.
     */
    @Operation(summary = "Get all users", description = "Retrieve all users with pagination and sorting")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    @GetMapping
    public ResponseEntity<Response<List<UserDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username,asc") String[] sort) {
        log.info("[#getAllUsers] is calling");
        Response<List<UserDto>> response = userService.getAllUsersWithPagination(page, size, sort);
        return ResponseEntity.ok(response);
    }

    /**
     * Устанавливает роль для пользователя. Доступно только для пользователей с ролью ADMIN.
     *
     * @param role роль, которую нужно установить.
     * @param id ID пользователя, для которого устанавливается роль.
     * @return ResponseEntity с обновленным пользователем.
     */
    @Operation(summary = "Set role for a user", description = "Set a role for a user")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    @PostMapping(value = "/{id}/role")
    public ResponseEntity<Response<UserDto>> setRole(@RequestParam String role, @PathVariable Long id) {
        log.info("[#setRole] is calling");
        Response<UserDto> response = userService.setRole(role, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет информацию о пользователе. Доступно только для пользователей с ролью ADMIN.
     *
     * @param userDto DTO с новыми данными пользователя.
     * @param id ID пользователя, информацию о котором нужно обновить.
     * @return ResponseEntity с обновленным пользователем.
     */
    @Operation(summary = "Update a user", description = "Update user information")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> update(@RequestBody UserDto userDto, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response<UserDto> response = userService.updateUser(id, userDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет пользователя по его ID. Доступно только для пользователей с ролью ADMIN.
     *
     * @param id ID пользователя, которого нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении пользователя.
     */
    @Operation(summary = "Delete a user", description = "Delete a user by their ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        userService.deleteUser(id);
        return ResponseEntity.ok(new Response<>("User deleted successfully!", "ID: " + id));
    }
}
