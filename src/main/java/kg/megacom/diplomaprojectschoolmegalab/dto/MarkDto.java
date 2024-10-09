package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarkDto {
    private Long id;
    private Integer mark;
    private Long studentId;
    private Long lessonId;
}
