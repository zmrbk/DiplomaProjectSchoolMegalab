package kg.megacom.diplomaprojectschoolmegalab.controller;


import kg.megacom.diplomaprojectschoolmegalab.dto.AnnouncementDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.AnnouncementService;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AnnouncementController - это REST-контроллер, который управляет объявлениями
 * в системе управления школой. Он предоставляет конечные точки для создания,
 * получения, обновления и удаления объявлений.
 *
 * <p>Все ответы обернуты в общий объект Response.</p>
 *
 * <p>Контроллер использует {@link AnnouncementService} для выполнения
 * бизнес-логики, связанной с объявлениями.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/announcements")
@Slf4j
public class AnnouncementController {

    private final AnnouncementService announcementService;

    /**
     * Создает новое объявление.
     *
     * @param announcementDto объект передачи данных, содержащий детали объявления
     * @return ResponseEntity с созданным объявлением и сообщением об успехе
     * @throws IllegalArgumentException если входные данные некорректны
     */
    @PostMapping
    public ResponseEntity<Response<AnnouncementDto>> create(@RequestBody AnnouncementDto announcementDto) {
        log.info("[#createAnnouncement] is calling");
        try {
            AnnouncementDto newAnnouncement = announcementService.create(announcementDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Объявление создано", newAnnouncement));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Некорректный ввод", null));
        }
    }

    /**
     * Получает объявление по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return ResponseEntity с данными объявления
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<AnnouncementDto>> getById(@PathVariable Long id) {
        log.info("[#getGradeById] is calling");
        Response<AnnouncementDto> response = announcementService.getById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Получает все объявления.
     *
     * @return ResponseEntity со списком всех объявлений
     */
    @GetMapping
    public ResponseEntity<Response<List<AnnouncementDto>>> getAll() {
        log.info("[#getAllGrades] is calling");
        Response<List<AnnouncementDto>> response = announcementService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет существующее объявление.
     *
     * @param announcementDto обновленные данные объявления
     * @param id идентификатор объявления для обновления
     * @return ResponseEntity с обновленным объявлением
     * @throws RuntimeException если обновление объявления невозможно
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnnouncement(@RequestBody AnnouncementDto announcementDto, @PathVariable Long id) {
        try {
            Response<AnnouncementDto> response = announcementService.update(announcementDto, id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Удаляет объявление по его идентификатору.
     *
     * @param id идентификатор объявления для удаления
     * @return ResponseEntity с сообщением об успехе, если удаление успешно
     * @throws EntityNotFoundException если объявление не существует
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        try {
            announcementService.delete(id);
            return ResponseEntity.ok(("Удалено!"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
