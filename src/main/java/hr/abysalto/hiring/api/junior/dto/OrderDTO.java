package hr.abysalto.hiring.api.junior.dto;



import hr.abysalto.hiring.api.junior.model.OrderStatus;
import hr.abysalto.hiring.api.junior.model.PaymentOption;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class OrderDTO {
    private BuyerDTO buyer;
    private OrderStatus status;
    private LocalDateTime orderTime;
    private PaymentOption paymentOption;
    private AddressDTO address;
    private String contactNumber;
    private String note;
    private List<OrderItemDTO> items;
    private BigDecimal totalPrice;
    private String currency;
}
