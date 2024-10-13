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

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Response<Void>> createMessage(@RequestBody MessageDto messageDto) {
        log.info("[#createMessage] is calling");
        messageService.create(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Message created successfully", null));
    }

    @GetMapping
    public ResponseEntity<Response<List<MessageDto>>> getAllMessages() {
        log.info("[#getAllMessages] is calling");
        Response<List<MessageDto>> response = messageService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MessageDto>> getMessageById(@PathVariable Long id) {
        log.info("[#getMessageById] is calling with ID: {}", id);
        MessageDto messageDto = messageService.getById(id);
        return ResponseEntity.ok(new Response<>("Message found", messageDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<MessageDto>> updateMessage(@PathVariable Long id, @RequestBody MessageDto messageDto) {
        log.info("[#updateMessage] is calling with ID: {}", id);
        Response<MessageDto> response = messageService.update(id, messageDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteMessage(@PathVariable Long id) {
        log.info("[#deleteMessage] is calling with ID: {}", id);
        messageService.delete(id);
        return ResponseEntity.ok(new Response<>("Message deleted successfully", null));
    }
}
