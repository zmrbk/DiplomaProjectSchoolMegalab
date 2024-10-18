package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkSubmissionDto {
    private Long homeworkId;
    private Long studentId;
}
