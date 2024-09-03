package com.example.CRUD.__08_24.Many_To_One.Controller;


import com.example.CRUD.__08_24.Many_To_One.Model.Address;
import com.example.CRUD.__08_24.Many_To_One.Model.User;
import com.example.CRUD.__08_24.Many_To_One.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/all")
    public List<Address> list(){
        return addressService.recieveAll();
    }

    @GetMapping("/byAddressId/{id}")
    public Optional<Address> getByID(@PathVariable Long id){
        return addressService.getByAddressId(id);
    }

    @GetMapping("/byUserId/{id}")
    public List<Address> getByUserID(@PathVariable Long id) throws RuntimeException {
        List<Address> listOfAddress = addressService.getByUserId(id);
        if ( ! listOfAddress.isEmpty() ){
            return listOfAddress;
        }
        else throw new RuntimeException("No such users found in address table");
    }

    @GetMapping("/userName/{name}")
    public List<Address> getByUserName(@PathVariable String name){
        List<Address> listOfAddress = addressService.findByUserNam(name);
        if ( !listOfAddress.isEmpty() ){
            return listOfAddress;
        }
        else return null;
    }



    @PostMapping("/addAddress")
    public Address insertAll( @RequestBody Address address){
        return addressService.insertAllAddress(address);
    }

    @PostMapping("/addUser")
    public User create(@RequestBody User user){
        return addressService.registerUser(user);
    }

    @PutMapping("/updateAddress/{id}")
    public Address recreate(@RequestBody Address address, @PathVariable Long id){
        return addressService.modify(address,id);
    }

    @PutMapping("/updateUser/{id}")
    public User change(@RequestBody User user, @PathVariable Long id){
        return addressService.put(user,id);
    }

    @DeleteMapping("deleteAddress/{id}")
    public String recondition(@PathVariable Long id){
         addressService.renovate(id);
         return "Address Deleted successfully";
    }

    @DeleteMapping("deleteUser/{id}")
    public String delete(@PathVariable Long id){
        return addressService.remove(id);
    }
}
