package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.JwtAuthenticationResponse;
import kg.megacom.diplomaprojectschoolmegalab.dto.SignInRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.SignUpRequest;
import kg.megacom.diplomaprojectschoolmegalab.service.PasswordResetService;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.AuthenticationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;
    private final PasswordResetService passwordResetService;

    //Регистрация
    @PostMapping(value = "/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        log.info("[#signUp] is calling");
        JwtAuthenticationResponse response = authenticationService.signUp(request);
        return ResponseEntity.ok(response);
    }
    //Вход
    @PostMapping(value = "/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("[#signIn] is calling");
        JwtAuthenticationResponse response = authenticationService.signIn(request);
        return ResponseEntity.ok(response);
    }

    // Эндпоинт для запроса восстановления пароля
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        passwordResetService.sendResetPasswordEmail(email);
        return ResponseEntity.ok("Password reset link has been sent to your email.");
    }

    // Эндпоинт для сброса пароля
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try{
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password has been successfully reset.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}