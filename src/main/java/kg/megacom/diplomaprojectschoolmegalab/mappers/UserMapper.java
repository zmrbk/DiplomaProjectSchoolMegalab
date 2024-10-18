package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Mapper для преобразования между сущностью {@link User} и DTO {@link UserDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link User} в {@link UserDto},
 * преобразования списков пользователей и обновления существующего пользователя на основе {@link UserDto}.
 * </p>
 */
@Component
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserMapper {

    private final RoleService roleService;

    /**
     * Преобразует объект {@link User} в объект {@link UserDto}.
     *
     * @param user Сущность, которую нужно преобразовать. Может быть null.
     * @return Преобразованный объект {@link UserDto} или null, если входной объект user равен null.
     */
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());

        // Преобразовать роли в имена ролей
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
        userDto.setRoles(roleNames);
        userDto.setCreationDate(user.getCreationDate());
        userDto.setActive(user.isActive());
        return userDto;
    }

    /**
     * Преобразует список объектов {@link User} в список объектов {@link UserDto}.
     *
     * @param users Список сущностей, которые нужно преобразовать. Может содержать null значения.
     * @return Список преобразованных объектов {@link UserDto}, игнорируя null значения.
     */
    public List<UserDto> toUserDtoList(List<User> users) {
        return users.stream()
                .map(this::toUserDto)
                .filter(Objects::nonNull) // Игнорировать null значения
                .collect(Collectors.toList());
    }

    /**
     * Обновляет сущность пользователя на основе {@link UserDto}.
     *
     * @param user   Сущность пользователя, которую нужно обновить.
     * @param userDto DTO, содержащий новые значения для обновления пользователя.
     * @return Обновленная сущность {@link User}.
     * @throws IllegalArgumentException Если user или userDto равны null.
     */
    public User updateUser(User user, UserDto userDto) {
        if (user == null || userDto == null) {
            throw new IllegalArgumentException("User or UserDto cannot be null");
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());

        // Преобразовать имена ролей в сущности ролей и задать их пользователю
        Set<Role> roles = userDto.getRoles().stream()
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user.setCreationDate(userDto.getCreationDate());
        user.setActive(userDto.isActive());
        return user;
    }

    public User toEntity(UserDto assignedBy) {

        User user = new User();
        user.setId(assignedBy.getId());
        user.setUsername(assignedBy.getUsername());
        user.setFirstName(assignedBy.getFirstName());
        user.setLastName(assignedBy.getLastName());
        user.setMiddleName(assignedBy.getMiddleName());
        user.setPhone(assignedBy.getPhone());
        user.setEmail(assignedBy.getEmail());
        Set<Role> roles = assignedBy.getRoles().stream()
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user.setCreationDate(assignedBy.getCreationDate());
        user.setActive(assignedBy.isActive());
        return user;
    }
}
