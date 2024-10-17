package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

/**
 * AuthController - это REST-контроллер, который управляет процессами аутентификации
 * и регистрации пользователей в системе. Он предоставляет конечные точки для регистрации,
 * входа в систему, запроса восстановления пароля и сброса пароля.
 *
 * <p>Контроллер использует {@link AuthenticationServiceImpl} для выполнения
 * бизнес-логики аутентификации и {@link PasswordResetService} для управления
 * восстановлением пароля.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
@Tag(name = "Authentication", description = "APIs for user authentication and password management")
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;
    private final PasswordResetService passwordResetService;

    /**
     * Регистрация нового пользователя.
     *
     * @param request объект запроса на регистрацию, содержащий данные пользователя
     * @return ResponseEntity с JWT-ответом, содержащим информацию об аутентификации
     */
    @Operation(summary = "Sign up a new user", description = "Register a new user with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping(value = "/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        log.info("[#signUp] is calling");
        JwtAuthenticationResponse response = authenticationService.signUp(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Вход пользователя в систему.
     *
     * @param request объект запроса на вход, содержащий данные для аутентификации
     * @return ResponseEntity с JWT-ответом, содержащим информацию об аутентификации
     */
    @Operation(summary = "Sign in user", description = "Authenticate the user with provided credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
    })
    @PostMapping(value = "/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("[#signIn] is calling");
        JwtAuthenticationResponse response = authenticationService.signIn(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Запрос на восстановление пароля.
     *
     * @param email адрес электронной почты пользователя, которому будет отправлена ссылка
     *              для сброса пароля
     * @return ResponseEntity с сообщением о статусе запроса
     */
    @Operation(summary = "Forgot password", description = "Send a reset password link to the provided email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset link sent successfully"),
            @ApiResponse(responseCode = "404", description = "Email not found", content = @Content)
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        passwordResetService.sendResetPasswordEmail(email);
        return ResponseEntity.ok("Ссылка для сброса пароля была отправлена на вашу электронную почту.");
    }

    /**
     * Сброс пароля пользователя.
     *
     * @param token токен для сброса пароля
     * @param newPassword новый пароль, который необходимо установить
     * @return ResponseEntity с сообщением о статусе сброса пароля
     */
    @Operation(summary = "Reset password", description = "Reset the user’s password with the provided token and new password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid token or password", content = @Content)
    })
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
