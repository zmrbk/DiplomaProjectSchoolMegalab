package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Charter;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CharterMapper {
    public CharterDto toCharterDto(Charter charter) {
        CharterDto charterDto = new CharterDto();
        charterDto.setId(charter.getId());
        charterDto.setTitle(charter.getTitle());
        charterDto.setDescription(charter.getDescription());
        charterDto.setEmployeeId(charter.getEmployee().getId());
        charterDto.setCreationDate(charter.getCreationDate());
        return charterDto;
    }

    public List<CharterDto> toCharterDtoList(List<Charter> all) {
        return all.stream()
                .map(this::toCharterDto)
                .collect(Collectors.toList());
    }

    public Charter toCharter(CharterDto charterDto, Employee employee) {
        Charter charter = new Charter();
        charter.setId(charterDto.getId());
        charter.setTitle(charterDto.getTitle());
        charter.setDescription(charterDto.getDescription());
        charter.setEmployee(employee);
        return charter;
    }
}
