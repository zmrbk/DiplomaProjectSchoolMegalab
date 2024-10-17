package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.*;
import kg.megacom.diplomaprojectschoolmegalab.service.PasswordResetService;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.AuthenticationServiceImpl;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@Slf4j
public class AdminController {
    private final AuthenticationServiceImpl authenticationService;
    private final PasswordResetService passwordResetService;
    private final UserServiceImpl userService;

    @PostMapping(value = "/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody SignUpRequest request) {
        log.info("[#register] is calling");
        JwtAuthenticationResponse response = authenticationService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/{id}/set-role")
    public ResponseEntity<Response<UserDto>> setRole(@RequestParam String role, @PathVariable Long id) {
        log.info("[#setRole] is calling");
        Response<UserDto> response = userService.setRole(role, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> update(@RequestBody UserDto userDto, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response<UserDto> response = userService.updateUser(id, userDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<UserDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username,asc") String[] sort) {
        log.info("[#getAllUsers] is calling");
        Response<List<UserDto>> response = userService.getAllUsersWithPagination(page, size, sort);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        userService.deleteUser(id);
        return ResponseEntity.ok(new Response<>("User deleted successfully!", "ID: " + id));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        passwordResetService.sendResetPasswordEmail(email);
        return ResponseEntity.ok("Ссылка для сброса пароля была отправлена на вашу электронную почту.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Пароль успешно сброшен.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
