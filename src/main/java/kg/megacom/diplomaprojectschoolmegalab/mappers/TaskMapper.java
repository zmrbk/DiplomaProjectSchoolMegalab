package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.TaskDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMapper {
    private final UserMapper userMapper; // Внедрение UserMapper

    public Task toEntity(TaskDto taskDto) {
        if (taskDto == null) {
            return null;
        }

        Task task = new Task();
        task.setId(taskDto.getId());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        task.setCreatedAt(taskDto.getCreatedAt());
        task.setCompletedAt(taskDto.getCompletedAt());

        if (taskDto.getAssignedBy() != null) {
            task.setAssignedBy(userMapper.toEntity(taskDto.getAssignedBy()));
        }

        if (taskDto.getCompletedBy() != null) {
            task.setCompletedBy(userMapper.toEntity(taskDto.getCompletedBy()));
        }

        return task;
    }

    public TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setDescription(task.getDescription());
        taskDto.setCompleted(task.isCompleted());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setCompletedAt(task.getCompletedAt());

        if (task.getAssignedBy() != null) {
            taskDto.setAssignedBy(userMapper.toUserDto(task.getAssignedBy()));
        }

        if (task.getCompletedBy() != null) {
            taskDto.setCompletedBy(userMapper.toUserDto(task.getCompletedBy()));
        }

        return taskDto;
    }
}
