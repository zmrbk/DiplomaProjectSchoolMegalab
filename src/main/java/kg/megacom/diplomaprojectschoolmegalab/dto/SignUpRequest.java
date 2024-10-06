package kg.megacom.diplomaprojectschoolmegalab.dto;

import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private String password;
    private Set<Role> roles;
}
