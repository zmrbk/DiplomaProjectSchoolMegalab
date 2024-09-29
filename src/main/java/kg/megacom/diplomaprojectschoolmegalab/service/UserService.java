package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;

import java.util.Optional;

public interface UserService {
    User create (User user);
    User getCurrentUser();
    Optional<User> getById(Long id);
    Response getUserResponseById(Long id);
    User getAdmin();
    Response getAllUsersWithPagination(int firstPage, int pageSize, String[] sort);
    Response updateUser(Long id, UserDto userDto);
}