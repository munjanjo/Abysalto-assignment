package hr.abysalto.hiring.api.junior.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

@Data
@AccessType(AccessType.Type.PROPERTY)
public class Order {
	@Id
	private Long orderNr;
	private Long buyerId;
	//private Buyer buyer;
	@Transient
	private OrderStatus orderStatus;

	@Column("order_status")
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

	@Column("payment_option")
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
