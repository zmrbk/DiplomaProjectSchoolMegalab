package kg.megacom.diplomaprojectschoolmegalab.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * Класс, представляющий роль пользователя в системе.
 * <p>
 * Этот класс реализует интерфейс {@link GrantedAuthority} и хранит
 * информацию о ролях, которые могут быть назначены пользователям.
 * </p>
 */
@Entity
@Table(name = "roles")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Role implements GrantedAuthority {

    /**
     * Уникальный идентификатор роли.
     * <p>
     * Генерируется автоматически при создании новой роли.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя роли.
     * <p>
     * Обязательное поле, должно быть уникальным в таблице ролей.
     * </p>
     */
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    /**
     * Связанные пользователи.
     * <p>
     * Связь "многие ко многим" с сущностью {@link User}.
     * Указывает на пользователей, которые имеют данную роль.
     * Это поле игнорируется при сериализации в формате JSON.
     * </p>
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;

    /**
     * Возвращает имя роли в формате, используемом Spring Security.
     * <p>
     * Имя роли возвращается в верхнем регистре и с префиксом "ROLE_".
     * Метод также логирует информацию о вызове.
     * </p>
     *
     * @return строка, представляющая авторитет роли.
     */
    @Override
    public String getAuthority() {
        log.info("[#getAuthority()] is calling for role: {}", this.roleName);
        return "ROLE_" + this.roleName.toUpperCase();
    }
}

