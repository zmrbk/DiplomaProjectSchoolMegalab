package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.MarkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Mark;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.MarkMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.MarkRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.MarkService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с оценками.
 *
 * Этот класс предоставляет функциональность для создания, обновления,
 * получения и удаления оценок, а также удаления оценок по идентификатору студента.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final MarkMapper markMapper;

    /**
     * Создание новой оценки.
     *
     * @param markDto объект, содержащий данные оценки для создания.
     */
    @Override
    public void create(MarkDto markDto) {
        Mark mark = markMapper.toMark(markDto);
        markRepository.save(mark);
    }

    /**
     * Получение списка всех оценок.
     *
     * @return список всех оценок в виде Response с сообщением и списком MarkDto.
     */
    @Override
    public Response<List<MarkDto>> getAll() {
        List<Mark> marks = markRepository.findAll();
        List<MarkDto> markDtoList = marks.stream()
                .map(markMapper::toMarkDto)
                .collect(Collectors.toList());
        return new Response<>("All marks retrieved successfully", markDtoList);
    }

    /**
     * Получение оценки по её идентификатору.
     *
     * @param id идентификатор оценки.
     * @return объект MarkDto с данными найденной оценки.
     * @throws EntityNotFoundException если оценка с указанным идентификатором не найдена.
     */
    @Override
    public MarkDto getById(Long id) {
        Mark mark = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mark not found"));
        return markMapper.toMarkDto(mark);
    }

    /**
     * Обновление данных оценки.
     *
     * @param id идентификатор оценки, которую нужно обновить.
     * @param markDto объект, содержащий обновленные данные оценки.
     * @return Response с сообщением об успешном обновлении и обновленным MarkDto.
     * @throws EntityNotFoundException если оценка с указанным идентификатором не найдена.
     */
    @Override
    public Response<MarkDto> update(Long id, MarkDto markDto) {
        Mark existingMark = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mark not found"));

        existingMark.setMark(markDto.getMark());
        existingMark.setStudent(markMapper.toStudent(markDto.getStudentId()));
        existingMark.setLesson(markMapper.toLesson(markDto.getLessonId()));

        Mark updatedMark = markRepository.save(existingMark);
        return new Response<>("Mark updated successfully", markMapper.toMarkDto(updatedMark));
    }

    /**
     * Удаление оценки по её идентификатору.
     *
     * @param id идентификатор оценки, которую нужно удалить.
     * @throws EntityNotFoundException если оценка с указанным идентификатором не найдена.
     */
    @Override
    public void delete(Long id) {
        Mark mark = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mark not found"));
        markRepository.delete(mark);
    }

    /**
     * Удаление всех оценок студента по его идентификатору.
     *
     * @param studentId идентификатор студента, для которого нужно удалить все оценки.
     */
    @Override
    public void deleteMarksByStudentId(Long studentId) {
        List<Mark> marksToDelete = markRepository.findByStudentId(studentId);
        markRepository.deleteAll(marksToDelete);
    }
}