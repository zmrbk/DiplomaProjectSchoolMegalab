package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DutyScheduleDto {

    private Long id;
    private String schedule;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
}