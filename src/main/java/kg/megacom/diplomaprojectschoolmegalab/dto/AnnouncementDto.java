package kg.megacom.diplomaprojectschoolmegalab.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementDto {
    private Long id;
    private String title;
    private String description;
    private String author;
    private LocalDateTime creationDate;
}
