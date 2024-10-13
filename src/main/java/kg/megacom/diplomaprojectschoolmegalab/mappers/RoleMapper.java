package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.RoleDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    // Преобразование из Entity в DTO (с одним именем роли)
    public static RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }
        return new RoleDto(
                role.getId(),
                List.of(role.getRoleName()),
                role.getUsers() != null && !role.getUsers().isEmpty()
                        ? role.getUsers().iterator().next().getId() : null
        );
    }

    // Преобразование RoleDto в Entity (для обновления или создания)
    public static Role toEntity(RoleDto roleDto) {
        if (roleDto == null || roleDto.getRoleNames() == null || roleDto.getRoleNames().isEmpty()) {
            return null;
        }
        Role role = new Role();
        role.setRoleName(roleDto.getRoleNames().get(0).toUpperCase());
        return role;
    }

    // Преобразование списка имен ролей в Set<Role>
    public static Set<Role> toEntitySet(RoleDto roleDto) {
        if (roleDto == null || roleDto.getRoleNames() == null) {
            return null;
        }
        return roleDto.getRoleNames().stream().map(roleName -> {
            Role role = new Role();
            role.setRoleName(roleName.toUpperCase());
            return role;
        }).collect(Collectors.toSet());
    }
}