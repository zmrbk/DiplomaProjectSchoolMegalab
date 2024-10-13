package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Charter;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.CharterMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.CharterRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.CharterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Реализация сервиса для управления уставами (Charter).
 *
 * Этот класс отвечает за создание, обновление, удаление и получение информации о уставах.
 */
@Service
@RequiredArgsConstructor
public class CharterServiceImpl implements CharterService {

    private final CharterRepository charterRepository;
    private final CharterMapper charterMapper;
    private final EmployeeRepository employeeRepository;

    /**
     * Создание нового устава.
     *
     * @param charterDto объект данных для создания устава, содержащий информацию о сотруднике.
     * @throws AccountNotFoundException если указанный сотрудник не найден.
     */
    @Override
    public void create(CharterDto charterDto) throws AccountNotFoundException {
        Employee employee = employeeRepository.findById(charterDto.getEmployeeId()).orElseThrow(
                () -> new AccountNotFoundException("Employee not found")
        );
        Charter charter = charterMapper.toCharter(charterDto, employee);
        charterRepository.save(charter);
    }

    /**
     * Обновление существующего устава.
     *
     * @param charterDto объект данных для обновления устава.
     * @param id идентификатор устава, который необходимо обновить.
     * @return ответ с сообщением об успешном обновлении и обновленными данными устава.
     * @throws AccountNotFoundException если устав или сотрудник не найдены.
     */
    @Override
    public Response<CharterDto> update(CharterDto charterDto, Long id) throws AccountNotFoundException {
        charterRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException("Charter not found"));

        Employee employee = employeeRepository.findById(charterDto.getEmployeeId()).orElseThrow(
                () -> new AccountNotFoundException("Employee not found")
        );

        charterDto.setId(id);
        Charter charter = charterMapper.toCharter(charterDto, employee);
        charterRepository.save(charter);
        return new Response<>("Charter updated", charterDto);
    }

    /**
     * Удаление устава по идентификатору.
     *
     * @param id идентификатор устава, который необходимо удалить.
     * @return ответ с сообщением об успешном удалении.
     * @throws EntityNotFoundException если устав с указанным идентификатором не найден.
     */
    @Override
    public Response<String> delete(Long id) throws EntityNotFoundException {
        charterRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Charter not found")
        );
        charterRepository.deleteById(id);
        return new Response<>("Charter deleted", "ID: " + id);
    }

    /**
     * Получение списка всех уставов.
     *
     * @return ответ с сообщением и списком всех уставов.
     */
    @Override
    public Response<List<CharterDto>> getAll() {
        List<Charter> charters = charterRepository.findAll();
        List<CharterDto> charterDtoList = charterMapper.toCharterDtoList(charters);
        return new Response<>("Charters", charterDtoList);
    }

    /**
     * Получение устава по идентификатору.
     *
     * @param id идентификатор устава, который необходимо получить.
     * @return ответ с сообщением и найденными данными устава.
     * @throws EntityNotFoundException если устав с указанным идентификатором не найден.
     */
    @Override
    public Response<CharterDto> getById(Long id) {
        Charter charter = charterRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Charter not found")
        );
        CharterDto charterDto = charterMapper.toCharterDto(charter);
        return new Response<>("Charter found", charterDto);
    }
}