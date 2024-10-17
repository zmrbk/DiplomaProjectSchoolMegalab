package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String description;
    private UserDto assignedBy;
    private UserDto completedBy;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}
