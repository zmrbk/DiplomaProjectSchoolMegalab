package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto create(TaskDto taskDto);

    TaskDto update(Long taskId, TaskDto taskDto);

    void delete(Long taskId);

    TaskDto getById(Long taskId);

    List<TaskDto> getAll();
}