package kg.megacom.diplomaprojectschoolmegalab.exceptions;
/**
 * Исключение, выбрасываемое, когда пытаются создать сущность, которая уже существует в системе.
 * <p>
 * Это исключение является производным от {@link RuntimeException} и может использоваться для
 * информирования об ошибках при добавлении сущностей в базу данных или другую структуру данных.
 * </p>
 */
public class EntityAlreadyExistsException extends RuntimeException {

    /**
     * Конструктор, создающий исключение с заданным сообщением.
     *
     * @param msg Сообщение, описывающее причину возникновения исключения.
     */
    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }
}