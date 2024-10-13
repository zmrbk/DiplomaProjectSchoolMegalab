package kg.megacom.diplomaprojectschoolmegalab.controller;

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

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response<String>> createRole(@RequestBody RoleDto roleDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        roleService.create(RoleMapper.toEntitySet(roleDto).iterator().next(), currentUser);
        return ResponseEntity.ok(new Response<>("Role created successfully", "Success"));
    }

    @GetMapping
    public ResponseEntity<Response<List<Role>>> getAllRoles() {
        Response<List<Role>> response = roleService.getAll();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<String>> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        Role roleToUpdate = RoleMapper.toEntity(roleDto);
        if (roleToUpdate == null) {
            return ResponseEntity.badRequest().body(new Response<>("Invalid Role Data", "Error"));
        }
        roleToUpdate.setId(id);
        Response<String> response = roleService.update(roleToUpdate);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteRole(@PathVariable Long id) {
        Role role = roleService.getById(id);
        roleService.delete(role);
        return ResponseEntity.ok(new Response<>("Role deleted successfully", "Success"));
    }
}
