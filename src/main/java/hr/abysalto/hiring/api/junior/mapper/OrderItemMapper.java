package hr.abysalto.hiring.api.junior.mapper;

import hr.abysalto.hiring.api.junior.dto.OrderItemDTO;
import hr.abysalto.hiring.api.junior.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapper {
    private OrderItemMapper(){}
    public static OrderItemDTO toDto(OrderItem item){
        if(item==null) return null;
        OrderItemDTO dto=new OrderItemDTO();
        dto.setName(item.getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }
    public static List<OrderItemDTO> toDtoList(List<OrderItem> items){
        if(items==null)return List.of();
        return items.stream()
                .map(OrderItemMapper::toDto)
                .collect(Collectors.toList());

    }
}
