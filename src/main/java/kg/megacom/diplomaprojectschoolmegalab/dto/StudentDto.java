package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StudentDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    private Long classId;
    private Long userId;
    private Long parentId;
    private String parentStatus;
}
