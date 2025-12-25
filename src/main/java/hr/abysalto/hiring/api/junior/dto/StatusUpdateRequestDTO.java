package hr.abysalto.hiring.api.junior.dto;

import hr.abysalto.hiring.api.junior.model.OrderStatus;
import lombok.Data;

@Data
public class StatusUpdateRequestDTO {
    private OrderStatus status;
}
