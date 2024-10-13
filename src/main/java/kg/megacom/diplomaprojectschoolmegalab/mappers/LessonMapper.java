package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.LessonDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Lesson;
import kg.megacom.diplomaprojectschoolmegalab.entity.Schedule;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.ScheduleRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LessonMapper {

    private final ScheduleRepository scheduleRepository;

    // Convert Lesson entity to LessonDto
    public LessonDto toLessonDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTopic(lesson.getTopic());
        lessonDto.setHomework(lesson.getHomework());
        lessonDto.setScheduleId(lesson.getSchedule().getId());
        lessonDto.setCreationDate(lesson.getCreationDate());
        return lessonDto;
    }

    // Convert LessonDto to Lesson entity
    public Lesson toLesson(LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setTopic(lessonDto.getTopic());
        lesson.setHomework(lessonDto.getHomework());
        lesson.setSchedule(toSchedule(lessonDto.getScheduleId()));
        lesson.setCreationDate(lessonDto.getCreationDate());
        return lesson;
    }

    // Retrieve Schedule entity based on scheduleId
    public Schedule toSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + scheduleId));
    }
}

