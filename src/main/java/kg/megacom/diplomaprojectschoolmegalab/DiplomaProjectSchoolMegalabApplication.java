package kg.megacom.diplomaprojectschoolmegalab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения для дипломного проекта "Школа Мегалаб".
 *
 * Этот класс содержит метод main, который является точкой входа в приложение.
 * Он запускает приложение с помощью Spring Boot.
 */
@SpringBootApplication
public class DiplomaProjectSchoolMegalabApplication {

    /**
     * Главный метод приложения.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(DiplomaProjectSchoolMegalabApplication.class, args);
    }
}