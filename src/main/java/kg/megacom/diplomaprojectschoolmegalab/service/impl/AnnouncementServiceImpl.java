package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.AnnouncementDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Announcement;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.AuthorMismatchException;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.AnnouncementMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.AnnouncementRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.AnnouncementService;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
/**
 * Реализация сервиса объявлений.
 *
 * Этот класс отвечает за операции, связанные с объявлениями, такие как создание, обновление,
 * удаление и получение объявлений.
 */
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper mapper;
    private final UserService userService;

    /**
     * Создает новое объявление.
     *
     * @param announcementDto объект DTO объявления, содержащий данные для создания.
     * @return объект DTO созданного объявления.
     */
    @Override
    public AnnouncementDto create(AnnouncementDto announcementDto) {
        Announcement announcement = mapper.toAnnouncement(announcementDto, userService.getCurrentUser());
        return mapper.toAnnouncementDto(announcementRepository.save(announcement));
    }

    /**
     * Обновляет существующее объявление.
     *
     * @param announcementDto объект DTO объявления, содержащий обновленные данные.
     * @param id идентификатор объявления, которое необходимо обновить.
     * @return объект Response с обновленным объявлением.
     * @throws AuthorMismatchException если имя автора объявления не совпадает с текущим пользователем.
     * @throws EntityNotFoundException если объявление с указанным идентификатором не найдено.
     */
    @Override
    public Response<AnnouncementDto> update(AnnouncementDto announcementDto, Long id) {
        User user = userService.getCurrentUser();
        String author = user.getFirstName() + " " + user.getLastName();

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Announcement not found"));

        if (!Objects.equals(announcementDto.getAuthor(), author)) {
            throw new AuthorMismatchException("The name author of the Announcement does not match the current name user");
        }

        announcement = mapper.toAnnouncement(announcementDto, user);
        announcement.setId(id);
        announcement.setCreationDate(announcement.getCreationDate()); // сохранение даты создания
        return new Response<>("Announcement is updated", mapper.toAnnouncementDto(announcementRepository.save(announcement)));
    }

    /**
     * Удаляет объявление по идентификатору.
     *
     * @param id идентификатор объявления, которое необходимо удалить.
     * @throws EntityNotFoundException если объявление с указанным идентификатором не найдено.
     */
    @Override
    public void delete(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new EntityNotFoundException("Announcement not found");
        }
        announcementRepository.deleteById(id);
    }

    /**
     * Получает все объявления.
     *
     * @return объект Response, содержащий список всех объявлений.
     */
    @Override
    public Response<List<AnnouncementDto>> getAll() {
        List<Announcement> announcements = announcementRepository.findAll();
        List<AnnouncementDto> announcementDtoList = mapper.toAnnouncementDtoList(announcements);
        return new Response<>("All Announcements are retrieved", announcementDtoList);
    }

    /**
     * Получает объявление по идентификатору.
     *
     * @param id идентификатор объявления, которое необходимо получить.
     * @return объект Response, содержащий найденное объявление.
     * @throws EntityNotFoundException если объявление с указанным идентификатором не найдено.
     */
    @Override
    public Response<AnnouncementDto> getById(Long id) {
        AnnouncementDto announcementDto = mapper.toAnnouncementDto(
                announcementRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Announcement not found")
                )
        );
        return new Response<>("Announcement is retrieved", announcementDto);
    }
}