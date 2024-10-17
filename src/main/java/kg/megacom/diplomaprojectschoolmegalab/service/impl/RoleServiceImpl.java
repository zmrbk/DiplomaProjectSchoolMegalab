package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityAlreadyExistsException;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.UnauthorizedAccessException;
import kg.megacom.diplomaprojectschoolmegalab.repository.RoleRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.RoleService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Реализация сервиса для работы с ролями.
 *
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения ролей.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    /**
     * Создание новой роли.
     *
     * @param role        объект роли, который нужно создать.
     * @param currentUser текущий пользователь, выполняющий действие.
     * @throws UnauthorizedAccessException если текущий пользователь не имеет прав ADMIN.
     * @throws EntityAlreadyExistsException если роль с таким именем уже существует.
     */
    @Override
    public void create(Role role, User currentUser) {
        if (currentUser == null || currentUser.getRoles()
                .stream().noneMatch(r -> r.getRoleName().equalsIgnoreCase("ADMIN"))) {
            throw new UnauthorizedAccessException("Only ADMIN can create a new role");
        }
        if (role.getRoleName() != null) {
            role.setRoleName(role.getRoleName().toUpperCase());
        }
        if (roleRepository.findByRoleName(role.getRoleName()).isPresent()) {
            throw new EntityAlreadyExistsException("Role already exists: " + role.getRoleName());
        }
        roleRepository.save(role);
    }

    /**
     * Обновление существующей роли.
     *
     * @param role объект роли с обновленной информацией.
     * @throws EntityNotFoundException если роль не найдена.
     * @throws EntityAlreadyExistsException если роль с таким именем уже существует.
     * @return ответ о результате операции обновления.
     */
    @Override
    public Response<String> update(Role role) {
        Role existingRole = roleRepository.findById(role.getId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + role.getId()));
        if (roleRepository.findByRoleName(role.getRoleName()).isPresent() &&
                !existingRole.getRoleName().equalsIgnoreCase(role.getRoleName())) {
            throw new EntityAlreadyExistsException("Role already exists with name: " + role.getRoleName());
        }
        existingRole.setRoleName(role.getRoleName().toUpperCase());
        roleRepository.save(existingRole);
        return new Response<>("Role updated successfully", "Success");
    }

    /**
     * Получение роли по имени.
     *
     * @param roleName имя роли, которую нужно получить.
     * @throws EntityNotFoundException если роль не найдена.
     * @return объект роли.
     */
    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    /**
     * Получение всех ролей.
     *
     * @return список всех ролей.
     */
    @Override
    public Response<List<Role>> getAll() {
        List<Role> roles = roleRepository.findAll();
        return new Response<>("All roles retrieved successfully", roles);
    }

    /**
     * Удаление роли.
     *
     * @param role объект роли, которую нужно удалить.
     */
    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    /**
     * Получение роли по её идентификатору.
     *
     * @param id идентификатор роли, которую нужно получить.
     * @throws EntityNotFoundException если роль не найдена.
     * @return объект роли.
     */
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + id));
    }
}