package hr.abysalto.hiring.api.junior.controller;

import hr.abysalto.hiring.api.junior.manager.OrderManager;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;
import hr.abysalto.hiring.api.junior.model.OrderStatus;
import jdk.jshell.Snippet;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
private final OrderManager orderManager;
public OrderController(OrderManager orderManager){
    this.orderManager=orderManager;
}
@GetMapping
    public ResponseEntity<Iterable<Order>> getAll(){
    return ResponseEntity.ok(orderManager.getAllOrders());
}

@GetMapping("/{id}")
public ResponseEntity<Order> getOrderById(@PathVariable Long id){
    Order order = orderManager.getOrder(id);
    return (order==null)?ResponseEntity.notFound().build():ResponseEntity.ok(order);
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
public ResponseEntity<List<Order>> sortOrdersByTotalPrice(@RequestParam(defaultValue = "false")boolean descending){
    return ResponseEntity.ok(orderManager.getAllOrdersSortedByTotal(descending));
}

@PostMapping
public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest req){
    if(req==null||req.getOrder()==null){
        return ResponseEntity.badRequest().build();
    }
    Order createdOrder = orderManager.createOrder(req.getOrder(),req.getItems());
    List<OrderItem> createdItems= orderManager.getOrderItems(createdOrder.getOrderId());
    OrderResponse response = new OrderResponse();
    response.setItems(createdItems);
    response.setOrder(createdOrder);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

@PatchMapping("/{id}/status")
public ResponseEntity<Order> changeStatus(@RequestBody StatusUpdateRequest req,@PathVariable Long id){
    if(req==null||req.getStatus()==null) {
        return ResponseEntity.badRequest().build();
    }

    Order order=orderManager.updateStatus(id,req.getStatus());

    return (order==null)? ResponseEntity.notFound().build():ResponseEntity.ok(order);

}


    @Data
    public static class CreateOrderRequest{
        private Order order;
        private List<OrderItem> items;
}
    @Data
    public static class OrderResponse{
        private Order order;
        private List<OrderItem> items;
    }
    @Data
    public static class StatusUpdateRequest{
    private OrderStatus status;
    }

}
