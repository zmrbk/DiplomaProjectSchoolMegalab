package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface CharterService {
    void create(CharterDto charterDto) throws AccountNotFoundException;
    Response<CharterDto> update(CharterDto charterDto, Long id) throws AccountNotFoundException;
    Response<String> delete(Long id)throws EntityNotFoundException;
    Response<List<CharterDto>> getAll();
    Response<CharterDto> getById(Long id);
}
