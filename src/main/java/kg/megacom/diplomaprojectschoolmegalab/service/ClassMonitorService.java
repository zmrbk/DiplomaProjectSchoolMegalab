package kg.megacom.diplomaprojectschoolmegalab.service;



import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.TaskDto;

import java.util.List;

public interface ClassMonitorService {

    // Составление отзыва об однокласснике
    ReviewDto writeReview(Long studentId, ReviewDto reviewDto);

    // Выполнение поручений от классного руководителя
    TaskDto completeTask(Long taskId, Long monitorId);

    // Составление графика дежурств по классу
    List<String> createDutySchedule(Long classId);
}