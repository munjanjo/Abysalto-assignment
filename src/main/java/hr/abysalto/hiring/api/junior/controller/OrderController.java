package hr.abysalto.hiring.api.junior.controller;

import hr.abysalto.hiring.api.junior.manager.OrderManager;
import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
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

}
