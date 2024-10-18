package kg.megacom.diplomaprojectschoolmegalab.exceptions;

/**
 * Исключение, выбрасываемое, когда сущность не найдена в системе.
 * <p>
 * Это исключение является производным от {@link RuntimeException} и может использоваться для
 * информирования об ошибках при попытках получить, обновить или удалить сущность,
 * которая отсутствует в базе данных или другой структуре данных.
 * </p>
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Конструктор, создающий исключение с заданным сообщением.
     *
     * @param msg Сообщение, описывающее причину возникновения исключения.
     */
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}