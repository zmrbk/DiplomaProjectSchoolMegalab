package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private List<String> roles;
    private LocalDateTime creationDate;
    private boolean isActive;
}
