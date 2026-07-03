package com.project.address.service;

import com.project.address.model.dto.AddressDto;
import com.project.address.model.dto.AddressRequest;
import com.project.address.model.dto.AddressRequestDto;
import com.project.address.model.entity.Address;

import java.util.List;

public interface AddressService {

    List<AddressDto> saveAddress(AddressRequest addressRequest);

    AddressDto updateAddress(Long addressId, Long empId, AddressRequestDto addressRequestDto);

    AddressDto getSingleAddress(Long id);

    List<AddressDto> getAllAddress();

    void deleteAddress(Long id);
}
