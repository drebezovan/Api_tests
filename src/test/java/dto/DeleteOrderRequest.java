package dto;

import lombok.Data;

@Data
public class DeleteOrderRequest {
    Long userId;
    String orderId;
}