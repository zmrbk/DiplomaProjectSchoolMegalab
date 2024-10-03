package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.repository.RoleRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public List<Role> findRolesByNames(List<String> roleNames) {
        return roleRepository.findAllByRoleNameIn(roleNames);
    }
    @Override
    public Role saveNewRole(Role newRole) {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }

    @Override
    public Role getRoleByName(String roleName) {
        return null;
    }

    @Override
    public List<Role> getAllRoleByUser(User user) {
        return List.of();
    }
}
