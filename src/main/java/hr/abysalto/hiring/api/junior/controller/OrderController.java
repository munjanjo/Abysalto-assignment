package hr.abysalto.hiring.api.junior.controller;

import hr.abysalto.hiring.api.junior.dto.CreateOrderRequestDTO;
import hr.abysalto.hiring.api.junior.dto.OrderDTO;
import hr.abysalto.hiring.api.junior.dto.OrderResponseDTO;
import hr.abysalto.hiring.api.junior.dto.StatusUpdateRequestDTO;
import hr.abysalto.hiring.api.junior.manager.OrderManager;
import hr.abysalto.hiring.api.junior.mapper.OrderMapper;
import hr.abysalto.hiring.api.junior.model.Buyer;
import hr.abysalto.hiring.api.junior.model.BuyerAddress;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;

import hr.abysalto.hiring.api.junior.repository.BuyerAddressRepository;
import hr.abysalto.hiring.api.junior.repository.BuyerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderManager orderManager;
    private final BuyerRepository buyerRepository;
    private final BuyerAddressRepository buyerAddressRepository;

    public OrderController(OrderManager orderManager,BuyerRepository buyerRepository,BuyerAddressRepository buyerAddressRepository) {
        this.orderManager = orderManager;
        this.buyerAddressRepository=buyerAddressRepository;
        this.buyerRepository=buyerRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<OrderDTO>> getAll() {
        List <OrderDTO> result=((List<Order>) orderManager.getAllOrders())
                .stream()
                .map(order -> {
                    List<OrderItem> items=orderManager.getOrderItems(order.getOrderId());
                    Buyer buyer=buyerRepository.findById(order.getBuyerId()).orElse(null);
                    BuyerAddress buyerAddress=buyerAddressRepository.findById(order.getDeliveryAddressId()).orElse(null);
                    return OrderMapper.toDTO(order,items,buyer,buyerAddress);
                }).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        Order order = orderManager.getOrder(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        List<OrderItem> items =
                orderManager.getOrderItems(id);

        Buyer buyer = buyerRepository
                .findById(order.getBuyerId())
                .orElse(null);

        BuyerAddress address = buyerAddressRepository
                .findById(order.getDeliveryAddressId())
                .orElse(null);

        return ResponseEntity.ok(
                OrderMapper.toDTO(order, items, buyer, address)
        );
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<OrderItem>> getOrderItems(@PathVariable Long id) {
        Order order = orderManager.getOrder(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        List<OrderItem> items = orderManager.getOrderItems(id);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Order>> sortOrdersByTotalPrice(@RequestParam(defaultValue = "false") boolean descending) {
        return ResponseEntity.ok(orderManager.getAllOrdersSortedByTotal(descending));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody CreateOrderRequestDTO req) {
        if (req == null || req.getOrder() == null) {
            return ResponseEntity.badRequest().build();
        }
        Order createdOrder = orderManager.createOrder(req.getOrder(), req.getItems());
        List<OrderItem> createdItems = orderManager.getOrderItems(createdOrder.getOrderId());
        OrderResponseDTO response = new OrderResponseDTO();
        response.setItems(createdItems);
        response.setOrder(createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> changeStatus(@RequestBody StatusUpdateRequestDTO req, @PathVariable Long id) {
        if (req == null || req.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        Order order = orderManager.updateStatus(id, req.getStatus());

        return (order == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(order);

    }


}
