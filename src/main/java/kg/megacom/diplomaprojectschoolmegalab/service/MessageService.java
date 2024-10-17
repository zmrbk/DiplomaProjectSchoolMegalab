package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.MessageDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface MessageService {
    void create(MessageDto messageDto);
    Response<MessageDto> update(Long id, MessageDto messageDto);
    Response<List<MessageDto>> getAll();
    MessageDto getById(Long id);
    void delete(Long id);
}
