package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void createRole(Role role);
    Role addNewRole(Role newRole);
    Response<String> updateRole(Role role);
    Response<List<Role>> getAllRoles();
    Role getRoleByName(String role_name);
    Response<Set<Role>> getAllRoleByUser(User user);
    void deleteRole(Role role);
}
