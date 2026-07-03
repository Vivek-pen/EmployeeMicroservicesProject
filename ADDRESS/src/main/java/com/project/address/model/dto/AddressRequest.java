package com.project.address.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddressRequest {

    private Long empId;
    private List<AddressRequestDto> addressRequestDtoList;
}
