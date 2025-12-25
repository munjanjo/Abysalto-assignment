package hr.abysalto.hiring.api.junior.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItemDTO {
    private String name;
    private short quantity;
    private BigDecimal price;
}
