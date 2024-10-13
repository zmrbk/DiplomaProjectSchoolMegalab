package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.AnnouncementDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Announcement;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnnouncementMapper {
    public AnnouncementDto toAnnouncementDto(Announcement announcement) {
        AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setId(announcement.getId());
        announcementDto.setTitle(announcement.getTitle());
        announcementDto.setDescription(announcement.getDescription());
        announcementDto.setAuthor(announcement.getAuthor());
        announcementDto.setCreationDate(announcement.getCreationDate());

        return announcementDto;
    }
    public Announcement toAnnouncement (AnnouncementDto announcementDto, User user) {
        Announcement announcement = new Announcement();
        announcement.setId(announcementDto.getId());
        announcement.setTitle(announcementDto.getTitle());
        announcement.setDescription(announcementDto.getDescription());
        announcement.setAuthor(user.getFirstName() + " " + user.getLastName());
        return announcement;
    }

    public List<AnnouncementDto> toAnnouncementDtoList(List<Announcement> all) {
        return all.stream()
                .map(this::toAnnouncementDto)
                .collect(Collectors.toList());
    }
}
