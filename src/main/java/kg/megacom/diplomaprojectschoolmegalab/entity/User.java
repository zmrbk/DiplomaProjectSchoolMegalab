package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Класс, представляющий пользователя системы.
 * <p>
 * Этот класс реализует интерфейс {@link UserDetails} для интеграции с системой безопасности.
 * Он содержит информацию о пользователе, такую как имя, фамилия, контактная информация,
 * а также роли, присвоенные пользователю.
 * </p>
 */
@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     * <p>
     * Генерируется автоматически при создании нового пользователя.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальное имя пользователя.
     * <p>
     * Обязательное поле. Должно быть уникальным в системе.
     * </p>
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * Имя пользователя.
     * <p>
     * Обязательное поле, представляющее имя пользователя.
     * </p>
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Фамилия пользователя.
     * <p>
     * Обязательное поле, представляющее фамилию пользователя.
     * </p>
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Отчество пользователя.
     * <p>
     * Необязательное поле, представляющее отчество пользователя.
     * </p>
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Телефонный номер пользователя.
     * <p>
     * Обязательное поле. Должно быть уникальным в системе.
     * </p>
     */
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    /**
     * Электронная почта пользователя.
     * <p>
     * Обязательное поле. Должно быть уникальным в системе.
     * </p>
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Пароль пользователя.
     * <p>
     * Обязательное поле, содержащий зашифрованный пароль пользователя.
     * </p>
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Дата и время создания пользователя.
     * <p>
     * Поле заполняется автоматически текущей датой и временем при создании пользователя.
     * </p>
     */
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * Статус активности пользователя.
     * <p>
     * По умолчанию пользователь активен (true). Указывает, может ли пользователь входить в систему.
     * </p>
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    /**
     * Роли, присвоенные пользователю.
     * <p>
     * Пользователь может иметь несколько ролей, которые хранятся в таблице m2m_users_roles.
     * </p>
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "m2m_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("[#getAuthorities()] is calling for user: {}", this.username);
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.isActive();
    }
}