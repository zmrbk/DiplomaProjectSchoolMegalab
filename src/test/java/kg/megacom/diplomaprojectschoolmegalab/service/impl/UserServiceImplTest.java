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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testCreateUserSuccess() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("newuser");
        mockUser.setEmail("newuser@example.com");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        // Act
        User createdUser = userService.create(mockUser);

        // Assert
        assertNotNull(createdUser);
        assertEquals("newuser", createdUser.getUsername());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    public void testCreateUserAlreadyExistsByUsername() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("existinguser");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // Act & Assert
        assertThrows(EntityAlreadyExistsException.class, () -> userService.create(mockUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateUserAlreadyExistsByEmail() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("newuser");
        mockUser.setEmail("existingemail@example.com");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("existingemail@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(EntityAlreadyExistsException.class, () -> userService.create(mockUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testGetByIdSuccess() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> user = userService.getById(1L);

        // Assert
        assertTrue(user.isPresent());
        assertEquals(1L, user.get().getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetByIdNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<User> user = userService.getById(1L);

        // Assert
        assertFalse(user.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserResponseByIdSuccess() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);

        UserDto mockUserDto = new UserDto();
        mockUserDto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userMapper.toUserDto(mockUser)).thenReturn(mockUserDto);

        // Act
        Response<UserDto> response = userService.getUserResponseById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getData().getId());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toUserDto(mockUser);
    }

    @Test
    public void testGetUserResponseByIdNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.getUserResponseById(1L));
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, never()).toUserDto(any(User.class));
    }

    @Test
    public void testSetRoleSuccess() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);
        Role mockRole = new Role();
//        mockRole.setName("USER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(roleService.getRoleByName("USER")).thenReturn(mockRole);

        // Act
//        Response<UserDto> response = userService.setRole("USER", 1L);

        // Assert
        Object response = null;
        assertNotNull(response);
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    public void testSetRoleUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.setRole("USER", 1L));
        verify(roleService, never()).getRoleByName(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testSetRoleNotFound() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(roleService.getRoleByName("ADMIN")).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.setRole("ADMIN", 1L));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testDeleteUserSuccess() {
        // Arrange
        User mockUser = new User();
        mockUser.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).delete(mockUser);
    }

    @Test
    public void testDeleteUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    public void testGetByUsernameSuccess() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
    }
}
