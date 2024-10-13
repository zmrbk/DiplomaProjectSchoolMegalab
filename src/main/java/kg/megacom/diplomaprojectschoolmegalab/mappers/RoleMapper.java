package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.RoleDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Mapper для преобразования между сущностью {@link Role} и DTO {@link RoleDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link Role} в {@link RoleDto}
 * и наоборот, а также для преобразования списков имен ролей в наборы ролей.
 * </p>
 */
@Component
public class RoleMapper {

    /**
     * Преобразует объект {@link Role} в объект {@link RoleDto}.
     *
     * @param role Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link RoleDto}, или {@code null}, если роль равна {@code null}.
     */
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

    /**
     * Преобразует объект {@link RoleDto} в объект {@link Role}.
     *
     * @param roleDto DTO, которое нужно преобразовать.
     * @return Преобразованный объект {@link Role}, или {@code null}, если DTO равно {@code null}
     *         или не содержит имен ролей.
     */
    public static Role toEntity(RoleDto roleDto) {
        if (roleDto == null || roleDto.getRoleNames() == null || roleDto.getRoleNames().isEmpty()) {
            return null;
        }
        Role role = new Role();
        role.setRoleName(roleDto.getRoleNames().get(0).toUpperCase());
        return role;
    }

    /**
     * Преобразует список имен ролей из {@link RoleDto} в набор {@link Role}.
     *
     * @param roleDto DTO, содержащее имена ролей.
     * @return Набор ролей, созданный на основе имен ролей из {@code roleDto}, или {@code null},
     *         если DTO равно {@code null} или не содержит имен ролей.
     */
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
