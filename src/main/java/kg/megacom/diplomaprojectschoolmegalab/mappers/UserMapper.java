package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setPhone(user.getPhone());
        userDto.setEmail(user.getEmail());
//        userDto.setRole(user.getRole());
        userDto.setCreationDate(user.getCreationDate());
        userDto.setActive(user.isActive());
        return userDto;
    }

    public List<UserDto> toUserDtoList(List<User> all) {
        return all.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());

    }

    public User updateUser(User user, UserDto userDto) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
//        user.setRole(userDto.getRole());
        user.setCreationDate(userDto.getCreationDate());
        user.setActive(userDto.isActive());
        return user;
    }
}
