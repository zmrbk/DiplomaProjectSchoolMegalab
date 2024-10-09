package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.AnnouncementDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface AnnouncementService {
    AnnouncementDto create(AnnouncementDto announcementDto);
    Response<AnnouncementDto> update(AnnouncementDto announcementDto, Long id);
    Response<List<AnnouncementDto>> getAll();
    Response<AnnouncementDto> getById(Long id);
    void delete(Long id);
}
