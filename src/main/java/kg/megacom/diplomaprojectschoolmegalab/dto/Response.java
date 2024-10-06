package kg.megacom.diplomaprojectschoolmegalab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    private String message;
    private T data;
}