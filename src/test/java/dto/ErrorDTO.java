package dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ErrorDTO {
    String code;
    String message;
}