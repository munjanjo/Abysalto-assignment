package hr.abysalto.hiring.api.junior.manager;

import hr.abysalto.hiring.api.junior.model.Buyer;

public interface BuyerManager {
	Iterable<Buyer> getAllBuyers();
	void save(Buyer buyer);
	Buyer getById(Long id);
	void deleteById(Long id);
}
