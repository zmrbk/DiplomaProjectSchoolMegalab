//package kg.megacom.diplomaprojectschoolmegalab.service.impl;
//
//import kg.megacom.diplomaprojectschoolmegalab.dto.JwtAuthenticationResponse;
//import kg.megacom.diplomaprojectschoolmegalab.dto.SignInRequest;
//import kg.megacom.diplomaprojectschoolmegalab.dto.SignUpRequest;
//import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
//import kg.megacom.diplomaprojectschoolmegalab.entity.User;
//import kg.megacom.diplomaprojectschoolmegalab.repository.RoleRepository;
////import kg.megacom.diplomaprojectschoolmegalab.service.UserServiceImpl;
////import kg.megacom.diplomaprojectschoolmegalab.service.JwtService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class AuthenticationServiceImplTest {
//
//    @Mock
//    private UserServiceImpl userService;
//
//    @Mock
//    private JwtService jwtService;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @InjectMocks
//    private AuthenticationServiceImpl authenticationService;
//
//    private SignUpRequest signUpRequest;
//    private SignInRequest signInRequest;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        // Prepare test data for SignUp and SignIn
//        signUpRequest = new SignUpRequest();
//        signUpRequest.setUsername("testUser");
//        signUpRequest.setPassword("password123");
//        signUpRequest.setEmail("test@example.com");
//        signUpRequest.setRoles(new HashSet<>(Collections.singletonList(new Role("ROLE_USER"))));
//
//        signInRequest = new SignInRequest();
//        signInRequest.setUsername("testUser");
//        signInRequest.setPassword("password123");
//
//        user = new User();
//        user.setUsername("testUser");
//        user.setEmail("test@example.com");
//        user.setPassword("encodedPassword");
//        user.setId(1L);
//    }
//
//    @Test
//    void testSignUp_Success() {
//        when(roleRepository.findByRoleName(any())).thenReturn(Optional.of(new Role("ROLE_USER")));
//        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");
//        when(userService.create(any(User.class))).thenReturn(user);
//        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
//        when(userService.getByUsername(any())).thenReturn(user);
//
//        JwtAuthenticationResponse response = authenticationService.signUp(signUpRequest);
//
//        assertEquals("jwtToken", response.getToken());
//        assertEquals(1L, response.getId());
//        verify(userService).create(any(User.class));
//        verify(passwordEncoder).encode(signUpRequest.getPassword());
//    }
//
//    @Test
//    void testSignUp_RoleNotFound() {
//        when(roleRepository.findByRoleName(any())).thenReturn(Optional.empty());
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> authenticationService.signUp(signUpRequest));
//        assertEquals("Role not found: ROLE_USER", exception.getMessage());
//        verify(userService, never()).create(any(User.class));
//    }
//
//    @Test
//    void testSignIn_Success() {
//        when(authenticationManager.authenticate(any())).thenReturn(null); // Simulate successful authentication
//        when(userService.userDetailsService().loadUserByUsername(signInRequest.getUsername())).thenReturn(user);
//        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
//        when(userService.getByUsername(signInRequest.getUsername())).thenReturn(user);
//
//        JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
//
//        assertEquals("jwtToken", response.getToken());
//        assertEquals(1L, response.getId());
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//    }
//
//    @Test
//    void testSignIn_InvalidCredentials() {
//        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Invalid credentials"));
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> authenticationService.signIn(signInRequest));
//        assertEquals("Invalid credentials", exception.getMessage());
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//    }
//}
