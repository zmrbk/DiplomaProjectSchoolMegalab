package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Parent;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.ParentMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.ParentRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.ParentService;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;
    private final UserService userService;

    @Override
    public Response createParent(ParentDto parentDto) throws AccountNotFoundException {
        if (userService.getById(parentDto.getUserId()).isEmpty()) {
            throw new AccountNotFoundException("User not found");
        }
        Parent parent = new Parent();
        parent.setUser(userService.getById(parentDto.getUserId()).orElseThrow(() -> new AccountNotFoundException("User not found")));

        return new Response("Parent created", parentDto);
    }

    @Override
    public Response updateParent(ParentDto parentDto, Long userId) throws AccountNotFoundException {
        if (userService.getById(parentDto.getUserId()).isEmpty()) {
            throw new AccountNotFoundException("User not found");
        }
        Parent parent = new Parent();
        parent.setId(parentDto.getId());
        parent.setUser(userService.getById(parentDto.getUserId()).orElseThrow(() -> new AccountNotFoundException("User not found")));
        parentRepository.save(parent);
        return new Response("Parent updated", parentMapper.toParentDto(parent));
    }

    @Override
    public Response deleteParent(Long id) throws EntityNotFoundException {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent not found");
        }
        return new Response("Parent deleted", null);
    }

    @Override
    public Response getAllParents() {
        List<ParentDto> list = parentMapper.toParentDtoList(parentRepository.findAll());
        return new Response("All parents", list);
    }

    @Override
    public Response getParentById(Long id) {
        Parent parent = parentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Parent not found"));
        return new Response("Get Parent ID", parentMapper.toParentDto(parent));
    }
}
