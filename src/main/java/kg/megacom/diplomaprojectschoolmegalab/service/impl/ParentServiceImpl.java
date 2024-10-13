package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Parent;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.ParentMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.ParentRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.ParentService;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;
    private final UserService userService;

    @Override
    public void create(ParentDto parentDto) throws EntityNotFoundException {
        if (userService.getById(parentDto.getUserId()).isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        Parent parent = new Parent();
        parent.setUser(userService.getById(parentDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        parentRepository.save(parent);
    }

    @Override
    public Response<ParentDto> update(ParentDto parentDto, Long id) throws EntityNotFoundException {
        if (userService.getById(parentDto.getUserId()).isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        Parent parent = new Parent();
        parent.setId(parentDto.getId());
        parent.setUser(userService.getById(parentDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        parentRepository.save(parent);
        return new Response<>("Parent updated", parentMapper.toParentDto(parent));
    }

    @Override
    public Response<String> delete(Long id) throws EntityNotFoundException {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent not found");
        }
        return new Response<>("Parent deleted", null);
    }

    @Override
    public Response<List<ParentDto>> getAll() {
        List<ParentDto> list = parentMapper.toParentDtoList(parentRepository.findAll());
        return new Response<>("All parents", list);
    }

    @Override
    public Parent getById(Long id) {
        return parentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Parent not found"));
    }

    public Response<ParentDto> getParentDtoById(Long id) {
        Parent parent = getById(id);
        ParentDto parentDto = parentMapper.toParentDto(parent);
        return new Response<>("Parent found", parentDto);
    }
}
