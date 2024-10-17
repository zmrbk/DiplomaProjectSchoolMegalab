package kg.megacom.diplomaprojectschoolmegalab.service.impl;


import kg.megacom.diplomaprojectschoolmegalab.dto.TaskDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Task;
import kg.megacom.diplomaprojectschoolmegalab.mappers.TaskMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.TaskRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto create(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDto update(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        task.setDescription(taskDto.getDescription());
        task.setAssignedBy(taskMapper.toEntity(taskDto).getAssignedBy());

        if (taskDto.isCompleted()) {
            task.setCompleted(true);
            task.setCompletedAt(LocalDateTime.now());
            task.setCompletedBy(taskMapper.toEntity(taskDto).getCompletedBy());
        }

        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskDto getById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
}