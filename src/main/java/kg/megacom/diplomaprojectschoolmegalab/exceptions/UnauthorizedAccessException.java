package kg.megacom.diplomaprojectschoolmegalab.exceptions;
/**
 * Исключение, выбрасываемое в случае несанкционированного доступа к ресурсу.
 * <p>
 * Это исключение наследует {@link RuntimeException} и может быть выброшено,
 * когда пользователь пытается выполнить операцию, на которую у него нет прав.
 * </p>
 */
public class UnauthorizedAccessException extends RuntimeException {

    /**
     * Конструктор, принимающий сообщение об ошибке.
     *
     * @param message Сообщение, описывающее причину исключения.
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}