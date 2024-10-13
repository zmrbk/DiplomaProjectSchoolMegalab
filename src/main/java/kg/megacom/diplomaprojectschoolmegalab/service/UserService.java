package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create (User user);
    User getCurrentUser();
    Optional<User> getById(Long id);
    Response<UserDto> getUserResponseById(Long id);
    Response<List<UserDto>> getAllUsersWithPagination(int firstPage, int pageSize, String[] sort);
    Response<UserDto> setRole(String role, Long id);
    Response<UserDto> updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}