package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.MessageDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Message;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.MessageMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.MessageRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.MessageService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с сообщениями.
 *
 * Этот класс предоставляет функциональность для создания, обновления,
 * получения и удаления сообщений.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    /**
     * Создание нового сообщения.
     *
     * @param messageDto объект, содержащий данные сообщения для создания.
     */
    @Override
    public void create(MessageDto messageDto) {
        log.info("[#createMessage] is calling");
        Message message = messageMapper.toEntity(messageDto);
        messageRepository.save(message);
        log.info("[#createMessage] successfully created");
    }

    /**
     * Обновление существующего сообщения.
     *
     * @param id идентификатор сообщения, которое нужно обновить.
     * @param messageDto объект, содержащий обновленные данные сообщения.
     * @return Response с сообщением об успешном обновлении и обновленным MessageDto.
     * @throws EntityNotFoundException если сообщение с указанным идентификатором не найдено.
     */
    @Override
    public Response<MessageDto> update(Long id, MessageDto messageDto) {
        log.info("[#updateMessage] is calling with ID: {}", id);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + id));

        message.setMessage(messageDto.getMessage());
        message.setIsRead(messageDto.getIsRead());
        message.setAuthor(messageMapper.toAuthor(messageDto.getAuthorId()));
        message.setReceiver(messageMapper.toReceiver(messageDto.getReceiverId()));

        messageRepository.save(message);
        log.info("[#updateMessage] successfully updated");
        return new Response<>("Message updated successfully", messageMapper.toDto(message));
    }

    /**
     * Получение списка всех сообщений.
     *
     * @return Response с сообщением и списком всех MessageDto.
     */
    @Override
    public Response<List<MessageDto>> getAll() {
        log.info("[#getAllMessages] is calling");
        List<MessageDto> messages = messageRepository.findAll().stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
        return new Response<>("All messages retrieved successfully", messages);
    }

    /**
     * Получение сообщения по его идентификатору.
     *
     * @param id идентификатор сообщения.
     * @return объект MessageDto с данными найденного сообщения.
     * @throws EntityNotFoundException если сообщение с указанным идентификатором не найдено.
     */
    @Override
    public MessageDto getById(Long id) {
        log.info("[#getMessageById] is calling with ID: {}", id);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + id));
        return messageMapper.toDto(message);
    }

    /**
     * Удаление сообщения по его идентификатору.
     *
     * @param id идентификатор сообщения, которое нужно удалить.
     * @throws EntityNotFoundException если сообщение с указанным идентификатором не найдено.
     */
    @Override
    public void delete(Long id) {
        log.info("[#deleteMessage] is calling with ID: {}", id);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + id));
        messageRepository.delete(message);
        log.info("[#deleteMessage] deleted successfully");
    }
}