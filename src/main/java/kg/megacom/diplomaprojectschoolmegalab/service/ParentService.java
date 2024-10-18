package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Parent;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface ParentService {
    void create(ParentDto parent) throws AccountNotFoundException;
    Response<ParentDto> update(ParentDto parentDto, Long id) throws AccountNotFoundException;
    Response<String> delete(Long id)throws EntityNotFoundException;
    Response<List<ParentDto>> getAll();
    Parent getById(Long id);
    List<ParentDto> getPendingApplications();

}
