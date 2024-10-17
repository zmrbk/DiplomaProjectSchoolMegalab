package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.AnnouncementDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Tag(name = "Announcement", description = "APIs for managing announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    /**
     * Создает новое объявление.
     *
     * @param announcementDto объект передачи данных, содержащий детали объявления
     * @return ResponseEntity с созданным объявлением и сообщением об успехе
     * @throws IllegalArgumentException если входные данные некорректны
     */
    @Operation(summary = "Create a new announcement", description = "Create a new announcement with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Announcement created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnnouncementDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PostMapping
    public ResponseEntity<Response<AnnouncementDto>> create(@RequestBody AnnouncementDto announcementDto) {
        log.info("[#createAnnouncement] is calling");
        try {
            AnnouncementDto newAnnouncement = announcementService.create(announcementDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Announcement is created successfully", newAnnouncement));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает объявление по его идентификатору.
     *
     * @param id идентификатор объявления
     * @return ResponseEntity с данными объявления
     */
    @Operation(summary = "Get announcement by ID", description = "Retrieve an announcement by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnnouncementDto.class))),
            @ApiResponse(responseCode = "404", description = "Announcement not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'SECRETARY')")
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
    @Operation(summary = "Get all announcements", description = "Retrieve a list of all announcements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcements retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'SECRETARY')")
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
    @Operation(summary = "Update an announcement", description = "Update an existing announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnnouncementDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict in updating the announcement", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'SECRETARY')")
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
    @Operation(summary = "Delete an announcement", description = "Delete an announcement by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Announcement not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'SECRETARY')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        try {
            announcementService.delete(id);
            return ResponseEntity.ok(("Announcement deleted successfully!"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
