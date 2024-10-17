package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.MessageDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class MessageController {

    private final MessageService messageService;

    /**
     * Создает новое сообщение.
     *
     * @param messageDto DTO с данными сообщения.
     * @return ResponseEntity с сообщением об успешном создании.
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteMessage(@PathVariable Long id) {
        log.info("[#deleteMessage] is calling with ID: {}", id);
        messageService.delete(id);
        return ResponseEntity.ok(new Response<>("Message deleted successfully!", null));
    }
}
