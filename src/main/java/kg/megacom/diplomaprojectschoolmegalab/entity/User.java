package kg.megacom.diplomaprojectschoolmegalab.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "m2m_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;  // Связь "многие ко многим" с ролями

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("[#getAuthorities()] is calling for user: {}", this.username);
        return this.roles;  // Directly return roles as GrantedAuthority
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Always return true or implement your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Always return true or implement your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Always return true or implement your logic
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Логируем вызов метода для отладки
//        log.info("[#getAuthority()] is calling for user: {}", this.roles);
//        return this.roles;
////        return roles.stream()
////                .map(Role::getAuthority)  // Используем метод getAuthority из Role
////                .collect(Collectors.toSet());
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.isActive();
//    }

}