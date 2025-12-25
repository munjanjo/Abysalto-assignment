package hr.abysalto.hiring.api.junior.mapper;

import hr.abysalto.hiring.api.junior.dto.BuyerDTO;
import hr.abysalto.hiring.api.junior.model.Buyer;

public class BuyerMapper {
    private BuyerMapper(){}
    public static BuyerDTO toDto(Buyer buyer){
        if(buyer==null)return null;
        BuyerDTO dto=new BuyerDTO();
        dto.setFirstName(buyer.getFirstName());
        dto.setLastName(buyer.getLastName());
        dto.setTitle(buyer.getTitle());
        return dto;
    }
}
