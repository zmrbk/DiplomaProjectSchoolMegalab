package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;

import java.util.List;

public interface RoleService {
    void create(Role role, User currentUser);
    Response<String> update(Role role);
    Role getRoleByName(String roleName);
    Response<List<Role>> getAll();
    void delete(Role role);
}
