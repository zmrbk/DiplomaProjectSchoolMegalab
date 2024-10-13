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

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    // Convert Message entity to MessageDto
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

    // Convert MessageDto to Message entity
    public Message toEntity(MessageDto messageDto) {
        Message message = new Message();
        message.setId(messageDto.getId());
        message.setMessage(messageDto.getMessage());
        message.setIsRead(messageDto.getIsRead());
        message.setAuthor(toAuthor(messageDto.getAuthorId()));
        message.setReceiver(toReceiver(messageDto.getReceiverId()));
        return message;
    }

    // Helper methods to fetch the related entities
    public Employee toAuthor(Long authorId) {
        return employeeRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with ID: " + authorId));
    }

    public User toReceiver(Long receiverId) {
        return userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found with ID: " + receiverId));
    }
}
