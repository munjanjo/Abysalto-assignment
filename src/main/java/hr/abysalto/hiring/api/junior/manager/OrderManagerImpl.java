package hr.abysalto.hiring.api.junior.manager;

import hr.abysalto.hiring.api.junior.model.Order;
import hr.abysalto.hiring.api.junior.model.OrderItem;
import hr.abysalto.hiring.api.junior.model.OrderStatus;
import hr.abysalto.hiring.api.junior.repository.OrderItemRepository;
import hr.abysalto.hiring.api.junior.repository.OrderRepository;
import jakarta.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class OrderManagerImpl implements OrderManager{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order createOrder(Order order, List<OrderItem> items){
        if(order == null){
            throw new IllegalArgumentException("Order is null");
        }
        if(order.getOrderStatus() == null){
            throw new IllegalArgumentException("status is null");
        }
        BigDecimal total = BigDecimal.ZERO;
        if(items!=null){
            for(OrderItem item: items){
                if(item == null)
                    continue;
                BigDecimal price = item.getPrice()==null? BigDecimal.ZERO : item.getPrice();
                short qty = item.getQuantity()==null? 0: item.getQuantity();
                total=total.add(price.multiply(BigDecimal.valueOf(qty)));
            }
        }
        order.setTotalPrice(total);
        Order savedOrder = orderRepository.save(order);
        if(items!=null){
            short itemNr=1;
            for(OrderItem item:items){
                if(item == null) continue;
                item.setOrderId(savedOrder.getOrderNr());
                if(item.getItemNr()==null){
                    item.setItemNr(itemNr++);
                }
                orderItemRepository.save(item);

            }
        }

        return savedOrder;
    }
    @Override
    public Order getOrder(Long orderNr){
        if(orderNr==null) return null;
        return orderRepository.findById(orderNr).orElse(null);
    }
    @Override
    public List<OrderItem> getOrderItems(Long orderNr){
        if(orderNr==null) return List.of();
        return orderItemRepository.findByOrderId(orderNr);
    }
    @Override
    public Iterable<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    @Override
    public List<Order> getAllOrdersSortedByTotal(boolean descending){
        List <Order> orders=new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);

        Comparator<Order> comparator=Comparator.comparing(
          order -> order.getTotalPrice()==null?BigDecimal.ZERO:order.getTotalPrice()
        );
        orders.sort(descending ? comparator.reversed(): comparator);
        return orders;
    }
    @Override
    @Transactional
    public Order updateStatus(Long orderNr, OrderStatus status){
        if(orderNr==null) return null;
        if(status==null) throw new IllegalArgumentException("order status is null");
        Order order = orderRepository.findById(orderNr).orElse(null);
        if(order==null)return null;
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }


}
