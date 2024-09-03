package com.example.CRUD.__08_24.Many_To_One.Model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="addressId")
    private  Long id;


    private String doorNumber;

    private String streetName;

    private String country;

    private Long pincode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="userId")
    private User user;

}

