package com.project.address.model.dto;

import com.project.address.model.enums.AddressType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressRequestDto {

    private Long id;
    private String street;
    private Long pinCode;
    private String city;
    private String country;
    private AddressType addressType;
}
