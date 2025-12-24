package hr.abysalto.hiring.api.junior.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("order")
@AccessType(AccessType.Type.PROPERTY)
public class Order {
	@Id
	private Long orderId;
	private Long buyerId;
	//private Buyer buyer;
	@Transient
	private OrderStatus orderStatus;

	@Column("ORDER_STATUS")
	public String getStringOrderStatus() {
		return this.orderStatus.toString();
	}

	public void setStringOrderStatus(String orderStatusString) {
		this.orderStatus = OrderStatus.fromString(orderStatusString);
	}

	private LocalDateTime orderTime;
	//	private List<OrderItem> orderItems;
	@Transient
	private PaymentOption paymentOption;

	@Column("PAYMENT_OPTION")
	public String getStringPaymentOption() {
		return this.paymentOption.toString();
	}

	public void setStringPaymentOption(String paymentOptionString) {
		this.paymentOption = PaymentOption.fromString(paymentOptionString);
	}

	private Long deliveryAddressId;
	//	private BuyerAddress deliveryAddress;
	private String contactNumber;
	private String currency;
	private BigDecimal totalPrice;
}
