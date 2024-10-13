package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * Сервис для работы с JWT (JSON Web Token).
 *
 * Этот класс предоставляет функциональность для генерации,
 * извлечения и проверки токенов JWT.
 */
@Service
public class JwtService {

    @Value("${secretKey}")
    private String jwtSigningKey;

    /**
     * Извлечение имени пользователя из токена.
     *
     * @param token токен JWT.
     * @return имя пользователя.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Генерация нового токена JWT.
     *
     * @param userDetails объект UserDetails, содержащий информацию о пользователе.
     * @return сгенерированный токен JWT.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("role", customUserDetails.getRoles());
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Проверка токена на валидность.
     *
     * @param token токен JWT.
     * @param userDetails объект UserDetails, содержащий информацию о пользователе.
     * @return true, если токен валиден, иначе false.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Извлечение данных из токена.
     *
     * @param token токен JWT.
     * @param claimsResolvers функция для извлечения данных.
     * @param <T> тип данных для извлечения.
     * @return извлеченные данные из токена.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация токена JWT с дополнительными данными.
     *
     * @param extraClaims карта дополнительных данных для включения в токен.
     * @param userDetails объект UserDetails, содержащий информацию о пользователе.
     * @return сгенерированный токен JWT.
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();
    }

    /**
     * Проверка токена на просроченность.
     *
     * @param token токен JWT.
     * @return true, если токен просрочен, иначе false.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты истечения токена.
     *
     * @param token токен JWT.
     * @return дата истечения токена.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение всех данных из токена.
     *
     * @param token токен JWT.
     * @return Claims, содержащие данные токена.
     */
    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    /**
     * Получение ключа для подписи токена.
     *
     * @return ключ для подписи.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}