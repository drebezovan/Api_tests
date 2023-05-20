package dto;

import lombok.Data;

@Data
public class CreateOrderRequest {
    Long userId;
    Integer tariffId;
}