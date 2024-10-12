package kg.megacom.diplomaprojectschoolmegalab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HomeworkDto {
    private Long id;
    private Boolean isDone;
    private String teacherReview;
    private Integer mark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    private Long studentId;
    private Long lessonId;
}
