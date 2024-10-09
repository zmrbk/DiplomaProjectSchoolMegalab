package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.MarkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Mark;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.MarkMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.MarkRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.MarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final MarkMapper markMapper;

    @Override
    public void create(MarkDto markDto) {
        Mark mark = markMapper.toMark(markDto);
        markRepository.save(mark);
    }

    @Override
    public Response<List<MarkDto>> getAll() {
        List<Mark> marks = markRepository.findAll();
        List<MarkDto> markDtoList = marks.stream()
                .map(markMapper::toMarkDto)
                .collect(Collectors.toList());
        return new Response<>("Marks retrieved successfully", markDtoList);
    }

    @Override
    public MarkDto getById(Long id) {
        Mark mark = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mark not found"));
        return markMapper.toMarkDto(mark);
    }

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

    @Override
    public void delete(Long id) {
        Mark mark = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mark not found"));
        markRepository.delete(mark);
    }

    @Override
    public void deleteMarksByStudentId(Long studentId) {
        List<Mark> marksToDelete = markRepository.findByStudentId(studentId);
        markRepository.deleteAll(marksToDelete);
    }
}
