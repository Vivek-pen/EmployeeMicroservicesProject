package com.project.address.model.entity;


import com.project.address.model.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long empId;
    private String street;
    private Long pinCode;
    private String city;
    private String country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
