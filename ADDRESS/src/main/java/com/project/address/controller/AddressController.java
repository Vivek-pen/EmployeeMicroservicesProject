package com.project.address.controller;

import com.project.address.model.dto.AddressDto;
import com.project.address.model.dto.AddressRequest;
import com.project.address.model.dto.AddressRequestDto;
import com.project.address.model.entity.Address;
import com.project.address.service.AddressService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressDto){
        List<AddressDto> response = addressService.saveAddress(addressDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{empId}/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long empId, @PathVariable Long addressId, @RequestBody AddressRequestDto addressRequestDto){
        AddressDto response = addressService.updateAddress(addressId, empId, addressRequestDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/all-address")
    public ResponseEntity<List<AddressDto>> getAllAddresses(){
        List<AddressDto> response = addressService.getAllAddress();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long addressId){
        AddressDto address = addressService.getSingleAddress(addressId);
        return new ResponseEntity<>(address,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }
}
