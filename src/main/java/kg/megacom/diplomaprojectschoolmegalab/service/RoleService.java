package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;

import java.util.List;

public interface RoleService {
    Role saveNewRole(Role newRole);
    List<Role> getAllRoles();
    Role getRoleByName(String roleName);
    List<Role> getAllRoleByUser(User user);
    List<Role> findRolesByNames(List<String> roleNames);
}
