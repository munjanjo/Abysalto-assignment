package hr.abysalto.hiring.api.junior.mapper;

import hr.abysalto.hiring.api.junior.dto.AddressDTO;
import hr.abysalto.hiring.api.junior.model.BuyerAddress;

public class BuyerAddressMapper {
    private BuyerAddressMapper(){}
    public static AddressDTO toDto(BuyerAddress buyerAddress){
        if(buyerAddress==null)return null;
        AddressDTO dto=new AddressDTO();
        dto.setCity(buyerAddress.getCity());
        dto.setStreet(buyerAddress.getStreet());
        dto.setHomeNumber(buyerAddress.getHomeNumber());
        return dto;
    }
}
