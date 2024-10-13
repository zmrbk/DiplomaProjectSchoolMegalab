package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Charter;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Mapper для преобразования между сущностью {@link Charter} и DTO {@link CharterDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link Charter} в {@link CharterDto}
 * и наоборот, а также для получения списка DTO из списка сущностей.
 * </p>
 */
@Component
public class CharterMapper {

    /**
     * Преобразует сущность {@link Charter} в {@link CharterDto}.
     *
     * @param charter Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link CharterDto}.
     */
    public CharterDto toCharterDto(Charter charter) {
        CharterDto charterDto = new CharterDto();
        charterDto.setId(charter.getId());
        charterDto.setTitle(charter.getTitle());
        charterDto.setDescription(charter.getDescription());
        charterDto.setEmployeeId(charter.getEmployee().getId());
        charterDto.setCreationDate(charter.getCreationDate());
        return charterDto;
    }

    /**
     * Преобразует список сущностей {@link Charter} в список DTO {@link CharterDto}.
     *
     * @param all Список сущностей, которые нужно преобразовать.
     * @return Список преобразованных объектов {@link CharterDto}.
     */
    public List<CharterDto> toCharterDtoList(List<Charter> all) {
        return all.stream()
                .map(this::toCharterDto)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует объект {@link CharterDto} в сущность {@link Charter}.
     *
     * @param charterDto DTO, которое нужно преобразовать.
     * @param employee Сущность {@link Employee}, связанная с чартером.
     * @return Преобразованная сущность {@link Charter}.
     */
    public Charter toCharter(CharterDto charterDto, Employee employee) {
        Charter charter = new Charter();
        charter.setId(charterDto.getId());
        charter.setTitle(charterDto.getTitle());
        charter.setDescription(charterDto.getDescription());
        charter.setEmployee(employee);
        return charter;
    }
}