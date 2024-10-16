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

import java.util.List;

/**
 * Реализация сервиса для работы с родителями.
 *
 * Этот класс предоставляет функциональность для создания, обновления,
 * удаления и получения информации о родителях.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;
    private final UserService userService;

    /**
     * Создание нового родителя.
     *
     * @param parentDto объект, содержащий данные родителя для создания.
     * @throws EntityNotFoundException если пользователь с указанным идентификатором не найден.
     */
    @Override
    public void create(ParentDto parentDto) throws EntityNotFoundException {
        if (userService.getById(parentDto.getUserId()).isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        Parent parent = new Parent();
        parent.setUser(userService.getById(parentDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        parentRepository.save(parent);
    }

    /**
     * Обновление существующего родителя.
     *
     * @param parentDto объект, содержащий обновленные данные родителя.
     * @param id идентификатор родителя, который нужно обновить.
     * @return Response с сообщением об успешном обновлении и обновленным ParentDto.
     * @throws EntityNotFoundException если пользователь с указанным идентификатором не найден.
     */
    @Override
    public Response<ParentDto> update(ParentDto parentDto, Long id) throws EntityNotFoundException {
        if (userService.getById(parentDto.getUserId()).isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        Parent parent = new Parent();
        parent.setId(parentDto.getId());
        parent.setUser(userService.getById(parentDto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        parentRepository.save(parent);
        return new Response<>("Parent updated successfully", parentMapper.toParentDto(parent));
    }

    /**
     * Удаление родителя по его идентификатору.
     *
     * @param id идентификатор родителя, который нужно удалить.
     * @return Response с сообщением об успешном удалении.
     * @throws EntityNotFoundException если родитель с указанным идентификатором не найден.
     */
    @Override
    public Response<String> delete(Long id) throws EntityNotFoundException {
        if (!parentRepository.existsById(id)) {
            throw new EntityNotFoundException("Parent not found");
        }
        parentRepository.deleteById(id); // Удаление родителя из репозитория
        return new Response<>("Parent deleted", null);
    }

    /**
     * Получение списка всех родителей.
     *
     * @return Response с сообщением и списком всех ParentDto.
     */
    @Override
    public Response<List<ParentDto>> getAll() {
        List<ParentDto> list = parentMapper.toParentDtoList(parentRepository.findAll());
        return new Response<>("All parents retrieved successfully", list);
    }

    /**
     * Получение родителя по его идентификатору.
     *
     * @param id идентификатор родителя.
     * @return объект Parent с данными найденного родителя.
     * @throws EntityNotFoundException если родитель с указанным идентификатором не найден.
     */
    @Override
    public Parent getById(Long id) {
        return parentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Parent not found"));
    }

    /**
     * Получение родителя в виде DTO по его идентификатору.
     *
     * @param id идентификатор родителя.
     * @return Response с сообщением и найденным ParentDto.
     */
    public Response<ParentDto> getParentDtoById(Long id) {
        Parent parent = getById(id); // Получение сущности Parent
        ParentDto parentDto = parentMapper.toParentDto(parent); // Преобразование в DTO
        return new Response<>("Parent found", parentDto);
    }
}