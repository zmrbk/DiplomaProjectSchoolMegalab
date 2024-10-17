package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.RoleDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.mappers.RoleMapper;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.RoleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Контроллер для управления ролями.
 * Предоставляет RESTful API для создания, обновления, получения и удаления ролей.
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Role", description = "APIs for managing roles")
public class RoleController {

    private final RoleServiceImpl roleService;

    /**
     * Создает новую роль.
     * Доступно только пользователям с ролью ADMIN.
     *
     * @param roleDto DTO с данными роли.
     * @return ResponseEntity с сообщением об успешном создании роли.
     */
    @Operation(summary = "Create a new role", description = "Create a new role in the system, accessible only to ADMIN users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Role Data", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response<String>> create(@RequestBody RoleDto roleDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        roleService.create(RoleMapper.toEntitySet(roleDto).iterator().next(), currentUser);
        return ResponseEntity.ok(new Response<>("Role is created successfully", "Success"));
    }

    /**
     * Обновляет роль по её ID.
     * Доступно только пользователям с ролью ADMIN.
     *
     * @param id ID роли, которую нужно обновить.
     * @param roleDto DTO с новыми данными роли.
     * @return ResponseEntity с сообщением об успешном обновлении роли.
     */
    @Operation(summary = "Update role by ID", description = "Update an existing role by its ID, accessible only to ADMIN users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Role Data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<String>> update(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        Role roleToUpdate = RoleMapper.toEntity(roleDto);
        if (roleToUpdate == null) {
            return ResponseEntity.badRequest().body(new Response<>("Invalid Role Data", "Error"));
        }
        roleToUpdate.setId(id);
        Response<String> response = roleService.update(roleToUpdate);
        return ResponseEntity.ok(response);
    }

    /**
     * Получает список всех ролей.
     *
     * @return ResponseEntity со списком ролей.
     */
    @Operation(summary = "Get all roles", description = "Retrieve a list of all roles, accessible to ADMIN and DIRECTOR users")
    @ApiResponse(responseCode = "200", description = "List of roles retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    @GetMapping
    public ResponseEntity<Response<List<Role>>> getAll() {
        Response<List<Role>> response = roleService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет роль по её ID.
     * Доступно только пользователям с ролью ADMIN.
     *
     * @param id ID роли, которую нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении роли.
     */
    @Operation(summary = "Delete role by ID", description = "Remove a role by its ID, accessible only to ADMIN users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Role not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        Role role = roleService.getById(id);
        roleService.delete(role);
        return ResponseEntity.ok(new Response<>("Role deleted successfully!", "Success"));
    }
}
