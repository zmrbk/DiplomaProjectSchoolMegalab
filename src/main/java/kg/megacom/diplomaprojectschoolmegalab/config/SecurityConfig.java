package kg.megacom.diplomaprojectschoolmegalab.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Конфигурация безопасности приложения, которая настраивает фильтры,
 * управление доступом и методы аутентификации.
 *
 * <p>Этот класс включает в себя настройку JWT аутентификации и
 * управление CORS. Он также определяет политику создания сессий
 * и параметры для аутентификации пользователей.
 *
 * <p>Класс помечен аннотациями {@link Configuration}, {@link EnableWebSecurity},
 * и {@link EnableGlobalMethodSecurity} для активации конфигурации Spring Security
 * и глобальной настройки метода безопасности.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserServiceImpl userService;

    // Конфигурация Jackson, чтобы без ошибок выводил LocalDateTime
    public class JacksonConfig {
        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper;
        }
    }

    @Bean
    public OpenAPI settingSwagger() {
        Info info = new Info();
        info.setTitle("Mega-lab School");
        info.setDescription("All APIs for School Megalab");
        info.version("1.0");
        return new OpenAPI().info(info);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // Своего рода отключение CORS (разрешение запросов со всех доменов)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                // Настройка доступа к конечным точкам
                .authorizeHttpRequests(request -> request
                        // Можно указать конкретный путь, * - 1 уровень вложенности, ** - любое количество уровней вложенности
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/endpoint", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/users/**","/roles/**").hasAnyRole("ADMIN", "DIRECTOR")
                        .requestMatchers("/employees/**").hasRole("DIRECTOR")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
