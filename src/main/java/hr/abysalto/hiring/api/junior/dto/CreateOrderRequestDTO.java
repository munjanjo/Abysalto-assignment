package hr.abysalto.hiring.api.junior.dto;

import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;
import lombok.Data;

import java.util.List;
@Data
public class CreateOrderRequestDTO {
        private Order order;
        private List<OrderItem> items;
}
