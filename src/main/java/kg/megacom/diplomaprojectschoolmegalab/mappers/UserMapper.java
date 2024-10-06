package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.service.RoleService;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleService roleService;

    // Преобразовать сущность User в UserDto
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

    // Преобразовать список сущностей User в список UserDto
    public List<UserDto> toUserDtoList(List<User> users) {
        return users.stream()
                .map(this::toUserDto)
                .filter(Objects::nonNull) // Ignore null values
                .collect(Collectors.toList());
    }

    // Обновление сущности пользователя на основе UserDto
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
}
