package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.AnnouncementDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Announcement;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.AnnouncementMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.AnnouncementRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.AnnouncementService;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper mapper;
    private final UserService userService;

    @Override
    public AnnouncementDto create(AnnouncementDto announcementDto) {
        Announcement announcement = mapper.toAnnouncement(announcementDto, userService.getCurrentUser());
        return mapper.toAnnouncementDto(announcementRepository.save(announcement));
    }

    @Override
    public Response<AnnouncementDto> update(AnnouncementDto announcementDto, Long id) throws RuntimeException {

        User user = userService.getCurrentUser();
        String author = user.getFirstName() + " " + user.getLastName();

        if (Objects.equals(announcementDto.getAuthor(), author)) {
            Announcement announcement = mapper.toAnnouncement(announcementDto, userService.getCurrentUser());
            announcement.setId(id);
            announcement.setCreationDate(announcementRepository.findById(id).get().getCreationDate());
            return new Response<>("Announcement is updated", mapper
                    .toAnnouncementDto(announcementRepository.save(announcement)));
        }
        throw new RuntimeException("The name author of the Announcement does not match the current name user");
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        announcementRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Announcement not found")
        );
        announcementRepository.deleteById(id);
    }

    @Override
    public Response<List<AnnouncementDto>> getAll() {
        List<Announcement> announcements = announcementRepository.findAll();
        List<AnnouncementDto> announcementDtoList = mapper.toAnnouncementDtoList(announcements);
        return new Response<>("All Announcements are retrieved", announcementDtoList);
    }

    @Override
    public Response<AnnouncementDto> getById(Long id) {
        AnnouncementDto announcementDto = mapper
                .toAnnouncementDto(announcementRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Announcement not found")
                ));
        return new Response<>("Announcement is retrieved", announcementDto);
    }
}
