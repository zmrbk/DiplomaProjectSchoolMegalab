package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.MessageDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.entity.Message;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Mapper для преобразования между сущностью {@link Message} и DTO {@link MessageDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link MessageDto} в {@link Message}
 * и наоборот.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    /**
     * Преобразует объект {@link Message} в объект {@link MessageDto}.
     *
     * @param message Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link MessageDto}.
     */
    public MessageDto toDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setMessage(message.getMessage());
        messageDto.setCreationDate(message.getCreationDate());
        messageDto.setIsRead(message.getIsRead());
        messageDto.setAuthorId(message.getAuthor().getId());
        messageDto.setReceiverId(message.getReceiver().getId());
        return messageDto;
    }

    /**
     * Преобразует объект {@link MessageDto} в сущность {@link Message}.
     *
     * @param messageDto DTO, которое нужно преобразовать.
     * @return Преобразованная сущность {@link Message}.
     * @throws EntityNotFoundException Если автор или получатель с указанным ID не найдены.
     */
    public Message toEntity(MessageDto messageDto) {
        Message message = new Message();
        message.setId(messageDto.getId());
        message.setMessage(messageDto.getMessage());
        message.setIsRead(messageDto.getIsRead());
        message.setAuthor(toAuthor(messageDto.getAuthorId()));
        message.setReceiver(toReceiver(messageDto.getReceiverId()));
        return message;
    }

    /**
     * Получает сущность {@link Employee} по ID автора.
     *
     * @param authorId ID автора.
     * @return Сущность {@link Employee}.
     * @throws EntityNotFoundException Если автор с указанным ID не найден.
     */
    public Employee toAuthor(Long authorId) {
        return employeeRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with ID: " + authorId));
    }

    /**
     * Получает сущность {@link User} по ID получателя.
     *
     * @param receiverId ID получателя.
     * @return Сущность {@link User}.
     * @throws EntityNotFoundException Если получатель с указанным ID не найден.
     */
    public User toReceiver(Long receiverId) {
        return userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found with ID: " + receiverId));
    }
}
