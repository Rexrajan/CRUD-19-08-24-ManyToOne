package com.example.CRUD.__08_24.Many_To_One.Repository;

import com.example.CRUD.__08_24.Many_To_One.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUserId(Long id);

    public void deleteByUserId(Long id);


}
