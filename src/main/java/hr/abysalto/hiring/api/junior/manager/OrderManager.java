package hr.abysalto.hiring.api.junior.manager;

import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;
import hr.abysalto.hiring.api.junior.model.OrderStatus;

import java.util.List;

public interface OrderManager {
    Order createOrder(Order order, List<OrderItem> items);
    Order getOrder(Long orderId);
    List <OrderItem> getOrderItems(Long orderId);
    Iterable<Order> getAllOrders();
    List<Order> getAllOrdersSortedByTotal(boolean descending);
    Order updateStatus (Long orderId, OrderStatus status);
}
