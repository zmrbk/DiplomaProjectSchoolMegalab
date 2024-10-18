package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Admin", description = "APIs for managing admin actions")
public class AdminController {

    private final AuthenticationServiceImpl authenticationService;
    private final PasswordResetService passwordResetService;
    private final UserServiceImpl userService;

    @Operation(summary = "Register a new admin", description = "Register a new admin with provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping(value = "/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody SignUpRequest request) {
        log.info("[#register] is calling");
        JwtAuthenticationResponse response = authenticationService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Set a role to a user", description = "Set a role to a user based on their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role set successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid role or user ID", content = @Content)
    })
    @PostMapping(value = "/{id}/set-role")
    public ResponseEntity<Response<UserDto>> setRole(@RequestParam String role, @PathVariable Long id) {
        log.info("[#setRole] is calling");
        Response<UserDto> response = userService.setRole(role, id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing user", description = "Update user details by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<UserDto>> update(@RequestBody UserDto userDto, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response<UserDto> response = userService.updateUser(id, userDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all users", description = "Retrieve a paginated list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Response<List<UserDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username,asc") String[] sort) {
        log.info("[#getAllUsers] is calling");
        Response<List<UserDto>> response = userService.getAllUsersWithPagination(page, size, sort);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a user by ID", description = "Delete a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        userService.deleteUser(id);
        return ResponseEntity.ok(new Response<>("User deleted successfully!", "ID: " + id));
    }

    @Operation(summary = "Send password reset link", description = "Send a password reset link to the provided email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset link sent successfully"),
            @ApiResponse(responseCode = "404", description = "Email not found", content = @Content)
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        passwordResetService.sendResetPasswordEmail(email);
        return ResponseEntity.ok("Ссылка для сброса пароля была отправлена на вашу электронную почту.");
    }

    @Operation(summary = "Reset password", description = "Reset password using the provided token and new password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid token or new password", content = @Content)
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
