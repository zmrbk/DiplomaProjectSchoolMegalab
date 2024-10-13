package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.AnnouncementDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/announcements")
@Slf4j
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<Response<AnnouncementDto>> create(@RequestBody AnnouncementDto announcementDto) {
        log.info("[#createAnnouncement] is calling");
        try {
            AnnouncementDto newAnnouncement = announcementService.create(announcementDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Announcement is created", newAnnouncement));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Invalid input", null));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<AnnouncementDto>> getById(@PathVariable Long id) {
        log.info("[#getGradeById] is calling");
        Response<AnnouncementDto> response = announcementService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<AnnouncementDto>>> getAll() {
        log.info("[#getAllGrades] is calling");
        Response<List<AnnouncementDto>> response = announcementService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnnouncement(@RequestBody AnnouncementDto announcementDto, @PathVariable Long id) {
        try {
            Response<AnnouncementDto> response = announcementService.update(announcementDto, id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        try {

            announcementService.delete(id);
            return ResponseEntity.ok(("Deleted!"));

        } catch (EntityNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
}
