package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicDto {
    private Long id;
    private String title;
    private String description;
}