package hr.abysalto.hiring.api.junior.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Buyer {
	@Id
	private Long buyerId;
	private String firstName;
	private String lastName;
	private String title;
}
