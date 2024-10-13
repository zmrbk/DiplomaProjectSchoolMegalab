package kg.megacom.diplomaprojectschoolmegalab.service.impl;


import kg.megacom.diplomaprojectschoolmegalab.dto.JwtAuthenticationResponse;
import kg.megacom.diplomaprojectschoolmegalab.dto.SignInRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.SignUpRequest;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Реализация сервиса аутентификации пользователей.
 *
 * Этот класс отвечает за регистрацию и аутентификацию пользователей с использованием
 * JWT (JSON Web Token).
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    /**
     * Регистрация нового пользователя.
     *
     * @param request объект запроса для регистрации пользователя, содержащий данные о пользователе и его ролях.
     * @return объект JwtAuthenticationResponse с JWT токеном и идентификатором пользователя.
     * @throws RuntimeException если роль не найдена в репозитории ролей.
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        log.info("Sign up request: {}", request);
        Set<Role> roles = new HashSet<>();
        for (Role role : request.getRoles()) {
            Role fetchedRole = roleRepository.findByRoleName(role.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + role.getRoleName()));
            roles.add(fetchedRole);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMiddleName(request.getMiddleName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);
        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, userService.getByUsername(user.getUsername()).getId());
    }

    /**
     * Аутентификация пользователя.
     *
     * @param request объект запроса для аутентификации пользователя, содержащий имя пользователя и пароль.
     * @return объект JwtAuthenticationResponse с JWT токеном и идентификатором пользователя.
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, userService.getByUsername(request.getUsername()).getId());
    }
}
