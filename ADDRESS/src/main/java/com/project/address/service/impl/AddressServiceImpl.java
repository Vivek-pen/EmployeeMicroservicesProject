package com.project.address.service.impl;

import com.project.address.exception.ResourceNotFoundException;
import com.project.address.model.dto.AddressDto;
import com.project.address.model.dto.AddressRequest;
import com.project.address.model.dto.AddressRequestDto;
import com.project.address.model.entity.Address;
import com.project.address.repository.AddressRepository;
import com.project.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        //TODO: check if Employee Exists
        List<Address> listToSave = this.saveOrUpdateAddressRequest(addressRequest);
        List<Address> savedAddress = addressRepository.saveAll(listToSave);
        return savedAddress.stream().map(address -> modelMapper.map(address,AddressDto.class)).toList();
    }


    @Override
    public AddressDto updateAddress(Long addressId, Long empId, AddressRequestDto addressRequestDto) {
        Address existingAddress = addressRepository.findById(addressId)
            .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));
        
        // Verify that the address belongs to the given employee
        if (!existingAddress.getEmpId().equals(empId)) {
            throw new ResourceNotFoundException("Address with id " + addressId + " does not belong to employee id " + empId);
        }
        
        // Update only the fields provided using setters
        existingAddress.setStreet(addressRequestDto.getStreet());
        existingAddress.setCity(addressRequestDto.getCity());
        existingAddress.setCountry(addressRequestDto.getCountry());
        existingAddress.setPinCode(addressRequestDto.getPinCode());
        existingAddress.setAddressType(addressRequestDto.getAddressType());
        
        Address updatedAddress = addressRepository.save(existingAddress);
        log.info("Address {} updated successfully for employee {}", addressId, empId);
        return modelMapper.map(updatedAddress, AddressDto.class);
    }

    @Override
    public AddressDto getSingleAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Address not found with id: "+id));
        return modelMapper.map(address,AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> all = addressRepository.findAll();
        if(all.isEmpty()){
            throw new ResourceNotFoundException("No address found");
        }
        return all.stream().map(address -> modelMapper.map(address,AddressDto.class)).toList();
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Address not found with id: "+id));
        addressRepository.delete(address);
    }

    private List<Address> saveOrUpdateAddressRequest(AddressRequest addressRequest){
        List<Address> listToSave = new ArrayList<>();
        for(AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()){
            Address address = new Address();
            address.setId(addressRequestDto.getId()!=null ? addressRequestDto.getId() :  null);
            address.setStreet(addressRequestDto.getStreet());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            address.setPinCode(addressRequestDto.getPinCode());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setEmpId(addressRequest.getEmpId());
            listToSave.add(address);
        }
        return listToSave;
    }
}
