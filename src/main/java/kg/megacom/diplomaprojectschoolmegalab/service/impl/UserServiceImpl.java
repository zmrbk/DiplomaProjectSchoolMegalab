package kg.megacom.diplomaprojectschoolmegalab.service.impl;


import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityAlreadyExistsException;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.UserMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.UserRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.RoleService;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.Set;
/**
 * Реализация сервиса для работы с пользователями.
 *
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения пользователей,
 * а также управления их ролями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    /**
     * Создание нового пользователя.
     *
     * @param user объект пользователя, который нужно создать.
     * @return созданный пользователь.
     * @throws EntityAlreadyExistsException если пользователь с таким именем или email уже существует.
     */
    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new EntityAlreadyExistsException("A user with this name already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException("A user with this email already exists");
        }
        log.info("Create user with username: {}", user.getUsername());
        return userRepository.save(user);
    }

    /**
     * Получение пользователя по идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return объект пользователя, если найден, иначе пустой Optional.
     */
    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Получение ответа с информацией о пользователе по его идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return ответ с информацией о пользователе.
     * @throws EntityNotFoundException если пользователь с указанным идентификатором не найден.
     */
    @Override
    public Response<UserDto> getUserResponseById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserDto userDto = userMapper.toUserDto(user);
        return new Response<>("User", userDto);
    }

    /**
     * Получение пользователя по имени пользователя.
     *
     * @param username имя пользователя.
     * @return объект пользователя.
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден.
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Получение текущего аутентифицированного пользователя.
     *
     * @return текущий пользователь.
     */
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    /**
     * Устаревший метод получения администратора.
     *
     * @deprecated Используйте другой способ управления ролями.
     * @return пользователь с ролью администратора.
     */
    @Deprecated
    public User getAdmin() {
        User user = getCurrentUser();
        Set<Role> roles = user.getRoles();
        Role adminRole = roleService.getRoleByName("ADMIN");
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    /**
     * Получение списка всех пользователей с пагинацией.
     *
     * @param firstPage номер первой страницы.
     * @param pageSize размер страницы.
     * @param sort массив, содержащий поле сортировки и направление.
     * @return ответ со списком пользователей.
     */
    @Override
    public Response<List<UserDto>> getAllUsersWithPagination(int firstPage, int pageSize, String[] sort) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(firstPage, pageSize, Sort.by(direction, sort[0]));
        Page<User> pages = userRepository.findAll(pageable);
        List<User> users = pages.getContent();
        List<UserDto> usersDto = userMapper.toUserDtoList(users);
        return new Response<>("All users retrieved successfully", usersDto);
    }

    /**
     * Установка роли пользователю.
     *
     * @param roleName имя роли.
     * @param userId идентификатор пользователя.
     * @return ответ с информацией о пользователе.
     * @throws EntityNotFoundException если пользователь с указанным идентификатором не найден.
     */
    @Override
    public Response<UserDto> setRole(String roleName, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleService.getRoleByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found: " + roleName);
        }
        Set<Role> currentRoles = user.getRoles();
        currentRoles.add(role);
        user.setRoles(currentRoles);
        userRepository.save(user);
        return new Response<>("User", userMapper.toUserDto(user));
    }

    /**
     * Обновление информации о пользователе.
     *
     * @param id идентификатор пользователя, которого нужно обновить.
     * @param userDto объект с новой информацией о пользователе.
     * @return ответ с обновленным пользователем.
     * @throws EntityNotFoundException если пользователь с указанным идентификатором не найден.
     */
    @Override
    public Response<UserDto> updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        User newUser = userMapper.updateUser(user, userDto);
        userRepository.save(newUser);
        UserDto newUserDto = userMapper.toUserDto(newUser);
        return new Response<>("User updated successfully", newUserDto);
    }

    /**
     * Удаление пользователя.
     *
     * @param id идентификатор пользователя, которого нужно удалить.
     * @throws EntityNotFoundException если пользователь с указанным идентификатором не найден.
     */
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

    /**
     * Получение сервиса для работы с пользователями.
     *
     * @return объект UserDetailsService.
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Загрузка пользователя по имени пользователя для аутентификации.
     *
     * @param username имя пользователя.
     * @return объект UserDetails.
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> getByOAuth2Id(String oauth2Id) {
        return userRepository.findByOauth2Id(oauth2Id);
    }
}