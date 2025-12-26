package hr.abysalto.hiring.api.junior.controller;

import hr.abysalto.hiring.api.junior.dto.CreateOrderRequestDTO;
import hr.abysalto.hiring.api.junior.dto.OrderDTO;
import hr.abysalto.hiring.api.junior.dto.OrderResponseDTO;
import hr.abysalto.hiring.api.junior.dto.StatusUpdateRequestDTO;
import hr.abysalto.hiring.api.junior.manager.OrderDtoManager;
import hr.abysalto.hiring.api.junior.manager.OrderManager;
import hr.abysalto.hiring.api.junior.mapper.OrderMapper;
import hr.abysalto.hiring.api.junior.model.Buyer;
import hr.abysalto.hiring.api.junior.model.BuyerAddress;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderManager orderManager;
    private final OrderDtoManager orderDtoManager;

    public OrderController(OrderManager orderManager, OrderDtoManager orderDtoManager) {
        this.orderManager = orderManager;
        this.orderDtoManager = orderDtoManager;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAll() {
        return ResponseEntity.ok(orderDtoManager.getAllOrderDtos(orderManager.getAllOrders()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        Order order=orderManager.getOrder(id);
        if(order==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderDtoManager.getOrderDto(order));
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
    public ResponseEntity<List<OrderDTO>> sortOrdersByTotalPrice(@RequestParam(defaultValue = "false") boolean descending) {
        List<Order> sorted= orderManager.getAllOrdersSortedByTotal(descending);
        return ResponseEntity.ok(orderDtoManager.getAllOrderDtos(sorted));
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
