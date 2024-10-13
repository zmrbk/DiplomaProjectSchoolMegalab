package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.AnnouncementDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Announcement;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper для преобразования между сущностью {@link Announcement} и DTO {@link AnnouncementDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link Announcement} в {@link AnnouncementDto}
 * и наоборот, а также для преобразования списков таких объектов.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AnnouncementMapper {

    /**
     * Преобразует сущность {@link Announcement} в {@link AnnouncementDto}.
     *
     * @param announcement Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link AnnouncementDto}.
     */
    public AnnouncementDto toAnnouncementDto(Announcement announcement) {
        AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setId(announcement.getId());
        announcementDto.setTitle(announcement.getTitle());
        announcementDto.setDescription(announcement.getDescription());
        announcementDto.setAuthor(announcement.getAuthor());
        announcementDto.setCreationDate(announcement.getCreationDate());

        return announcementDto;
    }

    /**
     * Преобразует объект {@link AnnouncementDto} в сущность {@link Announcement}.
     *
     * @param announcementDto DTO, которое нужно преобразовать.
     * @param user          Пользователь, создающий объявление.
     * @return Преобразованная сущность {@link Announcement}.
     */
    public Announcement toAnnouncement(AnnouncementDto announcementDto, User user) {
        Announcement announcement = new Announcement();
        announcement.setId(announcementDto.getId());
        announcement.setTitle(announcementDto.getTitle());
        announcement.setDescription(announcementDto.getDescription());
        announcement.setAuthor(user.getFirstName() + " " + user.getLastName());

        return announcement;
    }

    /**
     * Преобразует список сущностей {@link Announcement} в список {@link AnnouncementDto}.
     *
     * @param all Список сущностей, которые нужно преобразовать.
     * @return Список преобразованных объектов {@link AnnouncementDto}.
     */
    public List<AnnouncementDto> toAnnouncementDtoList(List<Announcement> all) {
        return all.stream()
                .map(this::toAnnouncementDto)
                .collect(Collectors.toList());
    }
}