package hr.abysalto.hiring.api.junior.manager;

import hr.abysalto.hiring.api.junior.dto.OrderDTO;
import hr.abysalto.hiring.api.junior.mapper.OrderMapper;
import hr.abysalto.hiring.api.junior.model.Buyer;
import hr.abysalto.hiring.api.junior.model.BuyerAddress;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;
import hr.abysalto.hiring.api.junior.repository.BuyerAddressRepository;
import hr.abysalto.hiring.api.junior.repository.BuyerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDtoManager {

    private final OrderManager orderManager;
    private final BuyerRepository buyerRepository;
    private final BuyerAddressRepository buyerAddressRepository;

    public OrderDtoManager(
            OrderManager orderManager,
            BuyerRepository buyerRepository,
            BuyerAddressRepository buyerAddressRepository
    ) {
        this.orderManager = orderManager;
        this.buyerRepository = buyerRepository;
        this.buyerAddressRepository = buyerAddressRepository;
    }

    public OrderDTO getOrderDto(Order order) {
        List<OrderItem> items =
                orderManager.getOrderItems(order.getOrderId());

        Buyer buyer =
                buyerRepository.findById(order.getBuyerId()).orElse(null);

        BuyerAddress address =
                buyerAddressRepository
                        .findById(order.getDeliveryAddressId())
                        .orElse(null);

        return OrderMapper.toDTO(order, items, buyer, address);
    }

    public List<OrderDTO> getAllOrderDtos(Iterable<Order> orders) {
        List<OrderDTO> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(getOrderDto(order));
        }
        return result;
    }
}