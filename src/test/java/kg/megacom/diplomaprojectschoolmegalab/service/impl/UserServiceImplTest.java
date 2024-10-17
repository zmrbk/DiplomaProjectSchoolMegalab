package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.UserDto;
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

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testUser");
        userDto.setFirstName("Test");
        userDto.setLastName("User");
        userDto.setEmail("test@example.com");
    }

    @Test
    void testGetUserResponseById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        Response<UserDto> response = userService.getUserResponseById(1L);

        assertNotNull(response);
        assertEquals("User", response.getMessage());
        assertEquals(userDto, response.getData());
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserResponseById_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserResponseById(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> userService.create(user));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUser_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.updateUser(any(User.class), any(UserDto.class))).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        Response<UserDto> response = userService.updateUser(1L, userDto);

        assertEquals("User updated successfully", response.getMessage());
        assertEquals(userDto, response.getData());
        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUser_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(1L, userDto));
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUser_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).delete(any(User.class));
    }
}
