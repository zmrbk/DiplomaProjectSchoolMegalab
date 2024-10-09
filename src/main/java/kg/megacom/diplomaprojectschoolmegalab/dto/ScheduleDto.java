package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleDto {
    private Long id;
    private String dayOfWeek;
    private Short quarter;
    private String dueTime;
    private String year;
    private Long subjectId;
    private Long teacherId;
    private Long classId;
    private Boolean isApprove;
}
