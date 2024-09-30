package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;

import javax.security.auth.login.AccountNotFoundException;

public interface ParentService {
    Response createParent(ParentDto parent) throws AccountNotFoundException;
    Response updateParent(ParentDto parentDto, Long userId) throws AccountNotFoundException;
    Response deleteParent(Long id)throws EntityNotFoundException;
    Response getAllParents();
    Response getParentById(Long id);
}
