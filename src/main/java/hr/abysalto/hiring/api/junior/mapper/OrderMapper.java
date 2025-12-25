package hr.abysalto.hiring.api.junior.mapper;

import hr.abysalto.hiring.api.junior.dto.BuyerDTO;
import hr.abysalto.hiring.api.junior.dto.OrderDTO;
import hr.abysalto.hiring.api.junior.dto.OrderResponseDTO;
import hr.abysalto.hiring.api.junior.model.Buyer;
import hr.abysalto.hiring.api.junior.model.BuyerAddress;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;

import java.util.List;

public class OrderMapper {
    private OrderMapper(){}
    public static OrderDTO toDTO(Order order, List<OrderItem> items, Buyer buyer, BuyerAddress buyerAddress){

        if(order==null) return null;
        OrderDTO dto=new OrderDTO();

        dto.setBuyer(BuyerMapper.toDto(buyer));
        dto.setStatus(order.getOrderStatus());
        dto.setOrderTime(order.getOrderTime());
        dto.setPaymentOption(order.getPaymentOption());
        dto.setAddress(BuyerAddressMapper.toDto(buyerAddress));
        dto.setContactNumber(order.getContactNumber());
        dto.setNote(order.getNote());
        dto.setItems(OrderItemMapper.toDtoList(items));
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCurrency(order.getCurrency());

        return dto;

    }

}
