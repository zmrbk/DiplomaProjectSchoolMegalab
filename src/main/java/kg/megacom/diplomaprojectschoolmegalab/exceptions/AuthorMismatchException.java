package kg.megacom.diplomaprojectschoolmegalab.exceptions;

/**
 * Исключение, выбрасываемое, когда имя автора объявления не совпадает с текущим пользователем.
 */
public class AuthorMismatchException extends RuntimeException {

    /**
     * Конструктор, принимающий сообщение об ошибке.
     *
     * @param message сообщение об ошибке.
     */
    public AuthorMismatchException(String message) {
        super(message);
    }

    /**
     * Конструктор, принимающий сообщение об ошибке и причину.
     *
     * @param message сообщение об ошибке.
     * @param cause причина исключения.
     */
    public AuthorMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}