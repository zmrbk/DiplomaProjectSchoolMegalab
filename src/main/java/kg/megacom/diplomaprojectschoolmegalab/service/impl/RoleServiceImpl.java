package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityAlreadyExistsException;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.RoleRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.UserRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.RoleService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private void validateRoleExists(String roleName) {
        if (roleRepository.findByRoleName(roleName).isPresent()) {
            throw new EntityAlreadyExistsException("Role already exists");
        }
    }

    @Override
    public void createRole(Role role) {
        validateRoleExists(role.getRoleName());
        roleRepository.save(role);
    }

    @Override
    public Role addNewRole(Role newRole) {
        return null;
    }

    @Override
    public Response<String> updateRole(Role role) {
        Role existingRole = roleRepository.findById(role.getId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        existingRole.setRoleName(role.getRoleName());
        roleRepository.save(existingRole);
        return new Response<>("Role updated successfully", "Success");
    }

    @Override
    public Response<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return new Response<>("Roles got successfully", roles);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    @Override
    public Response<Set<Role>> getAllRoleByUser(User user) {
        User foundUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Set<Role> roles = foundUser.getRoles();
        return new Response<>("Roles retrieved successfully", roles);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }
}
