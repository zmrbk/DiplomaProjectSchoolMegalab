package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.MessageDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления сообщениями.
 * Предоставляет RESTful API для создания, получения, обновления и удаления сообщений.
 */
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Message", description = "APIs for managing messages")
public class MessageController {

    private final MessageService messageService;

    /**
     * Создает новое сообщение.
     *
     * @param messageDto DTO с данными сообщения.
     * @return ResponseEntity с сообщением об успешном создании.
     */
    @Operation(summary = "Create a new message", description = "Add a new message to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Incorrect input", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PostMapping
    public ResponseEntity<Response<Void>> createMessage(@RequestBody MessageDto messageDto) {
        log.info("[#createMessage] is calling");
        messageService.create(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Message is created successfully", null));
    }

    /**
     * Получает список всех сообщений.
     *
     * @return ResponseEntity со списком сообщений.
     */
    @Operation(summary = "Get all messages", description = "Retrieve a list of all messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of messages retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "No messages found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping
    public ResponseEntity<Response<List<MessageDto>>> getAllMessages() {
        log.info("[#getAllMessages] is calling");
        Response<List<MessageDto>> response = messageService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Получает сообщение по его ID.
     *
     * @param id ID сообщения.
     * @return ResponseEntity с данными сообщения.
     */
    @Operation(summary = "Get message by ID", description = "Retrieve message details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "404", description = "Message not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<MessageDto>> getMessageById(@PathVariable Long id) {
        log.info("[#getMessageById] is calling with ID: {}", id);
        MessageDto messageDto = messageService.getById(id);
        return ResponseEntity.ok(new Response<>("Сообщение найдено", messageDto));
    }

    /**
     * Обновляет информацию о сообщении по его ID.
     *
     * @param id ID сообщения, которое нужно обновить.
     * @param messageDto DTO с новыми данными сообщения.
     * @return ResponseEntity с обновленной информацией о сообщении.
     */
    @Operation(summary = "Update message by ID", description = "Update an existing message by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "404", description = "Message not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<MessageDto>> updateMessage(@PathVariable Long id, @RequestBody MessageDto messageDto) {
        log.info("[#updateMessage] is calling with ID: {}", id);
        Response<MessageDto> response = messageService.update(id, messageDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет сообщение по его ID.
     *
     * @param id ID сообщения, которое нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @Operation(summary = "Delete message by ID", description = "Remove a message by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Message not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteMessage(@PathVariable Long id) {
        log.info("[#deleteMessage] is calling with ID: {}", id);
        messageService.delete(id);
        return ResponseEntity.ok(new Response<>("Message deleted successfully!", null));
    }
}
