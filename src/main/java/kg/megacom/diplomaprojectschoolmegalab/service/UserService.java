package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;

import java.util.Optional;

public interface UserService {
    User create (User user);
    User getCurrentUser();

}