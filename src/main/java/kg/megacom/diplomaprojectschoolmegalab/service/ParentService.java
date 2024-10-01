package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;

import javax.security.auth.login.AccountNotFoundException;

public interface ParentService {
    void create(ParentDto parent) throws AccountNotFoundException;
    Response update(ParentDto parentDto, Long id) throws AccountNotFoundException;
    Response delete(Long id)throws EntityNotFoundException;
    Response getAll();
    Response getById(Long id);
}
