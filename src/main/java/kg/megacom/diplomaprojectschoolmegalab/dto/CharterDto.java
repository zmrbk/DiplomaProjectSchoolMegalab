package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CharterDto {
    private Long id;
    private String title;
    private String description;
    private Long employeeId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate = LocalDateTime.now();
}
