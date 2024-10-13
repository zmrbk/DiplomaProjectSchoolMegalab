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

    public LessonDto toLessonDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTopic(lesson.getTopic());
        lessonDto.setHomework(lesson.getHomework());
        lessonDto.setScheduleId(lesson.getSchedule().getId());
        lessonDto.setCreationDate(lesson.getCreationDate());
        return lessonDto;
    }

    public Lesson toLesson(LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setTopic(lessonDto.getTopic());
        lesson.setHomework(lessonDto.getHomework());
        lesson.setSchedule(toSchedule(lessonDto.getScheduleId()));
        lesson.setCreationDate(lessonDto.getCreationDate());
        return lesson;
    }

    public Schedule toSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + scheduleId));
    }
}

