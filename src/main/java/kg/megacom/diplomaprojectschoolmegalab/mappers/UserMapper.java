package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleService roleService;

    public UserDto toUserDto(User user) {
        if (user == null) {
            return null; // Или бросьте исключение, если нужно
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());

        // Получение имен ролей из сущности
        userDto.setRoles(user.getRoles().stream()
                .map(Role::getRoleName) // Предполагается, что Role имеет метод getRoleName()
                .collect(Collectors.toList())); // Собрать в List<String>

        userDto.setCreationDate(user.getCreationDate());
        userDto.setActive(user.isActive());
        return userDto;
    }

    // Convert list of User entities to list of UserDtos
    public List<UserDto> toUserDtoList(List<User> users) {
        return users.stream()
                .map(this::toUserDto)
                .filter(Objects::nonNull) // Игнорируем null-значения
                .collect(Collectors.toList());
    }

    public User updateUser(User user, UserDto userDto) {
        if (user == null || userDto == null) {
            throw new IllegalArgumentException("User or UserDto cannot be null");
        }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());

        // Получение ролей на основе имен ролей из DTO
        List<Role> roles = roleService.findRolesByNames(userDto.getRoles());
        user.setRoles(new HashSet<>(roles)); // Установка ролей в User

        user.setCreationDate(userDto.getCreationDate());
        user.setActive(userDto.isActive());
        return user;
    }

    // Helper method to map roles
    // В вашем UserMapper
    private List<Role> mapRoles(List<String> roleNames) {
        return roleNames != null && !roleNames.isEmpty()
                ? roleService.findRolesByNames(roleNames) // Получение ролей по именам
                : List.of(); // Вернуть пустой список, если roleNames пуст
    }

}





//package kg.megacom.diplomaprojectschoolmegalab.mappers;
//
//import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
//import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
//import kg.megacom.diplomaprojectschoolmegalab.entity.User;
//import kg.megacom.diplomaprojectschoolmegalab.service.RoleService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class UserMapper {
//
//    private final RoleService roleService;
//
//    public UserDto toUserDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setUsername(user.getUsername());
//        userDto.setFirstName(user.getFirstName());
//        userDto.setLastName(user.getLastName());
//        userDto.setMiddleName(user.getMiddleName());
//        userDto.setPhone(user.getPhone());
//        userDto.setEmail(user.getEmail());
//        userDto.setRole(user.getRoleList());
//        userDto.setCreationDate(user.getCreationDate());
//        userDto.setActive(user.isActive());
//        return userDto;
//    }
//
//    public List<UserDto> toUserDtoList(List<User> all) {
//        return all.stream()
//                .map(this::toUserDto)
//                .collect(Collectors.toList());
//
//    }
//
//    public User updateUser(User user, UserDto userDto) {
//        user.setFirstName(userDto.getFirstName());
//        user.setLastName(userDto.getLastName());
//        user.setMiddleName(userDto.getMiddleName());
//        user.setPhone(userDto.getPhone());
//        user.setEmail(userDto.getEmail());
//        user.setRoleList(userDto.getRole());
//        user.setCreationDate(userDto.getCreationDate());
//        user.setActive(userDto.isActive());
//        return user;
//    }
//}
