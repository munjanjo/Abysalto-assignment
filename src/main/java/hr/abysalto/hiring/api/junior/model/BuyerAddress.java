package hr.abysalto.hiring.api.junior.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BuyerAddress {
	@Id
	private Long buyerAddressId;
	private String city;
	private String street;
	private String homeNumber;
}
