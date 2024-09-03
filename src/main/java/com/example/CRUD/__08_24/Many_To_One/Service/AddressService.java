package com.example.CRUD.__08_24.Many_To_One.Service;

import com.example.CRUD.__08_24.Many_To_One.Model.Address;
import com.example.CRUD.__08_24.Many_To_One.Model.User;
import com.example.CRUD.__08_24.Many_To_One.Repository.AddressRepository;
import com.example.CRUD.__08_24.Many_To_One.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    public List<Address> recieveAll(){
        return addressRepository.findAll();
    }

    public Optional<Address> getByAddressId(Long id){
       return addressRepository.findById(id);
    }

    public List<Address> getByUserId(Long id) {
       return addressRepository.findByUserId(id);
    }

    public List<Address> findByUserNam(String name) {
        List<User> listOfNames = userRepository.findByUserName(name);
        List<Address> addressByUserName = new ArrayList<>();

        if( !listOfNames.isEmpty() ){
            for(User i : listOfNames){
                Long userId = i.getId();
                List<Address> listOfAddress = addressRepository.findByUserId(userId);
                addressByUserName.addAll(listOfAddress);
            }

        }

        return addressByUserName;

    }

    /*public ArrayList<Address> findByUserName(String name){

        Optional<List<User>> userList = repository.findByUserName(name);

        ArrayList<Address> collectionList = new ArrayList<>() ;

        userList.ifPresent( list -> list.forEach(element -> {
            Optional<List<Address>> addrList = addresRepository.findByUserId(element.getId());

            addrList.ifPresent(
                    addr -> addr.forEach(e ->  collectionList.add(e)));

        }));


        return  collectionList;
    }*/


    public Address insertAllAddress(Address address){
        User userInAddressTable =  address.getUser();
        if(userInAddressTable.getId() != null){
            User userTable = userRepository.findById(userInAddressTable.getId()).orElseThrow(()-> new RuntimeException("UserId not found in user table"));
            address.setUser(userTable);
        }
    return addressRepository.save(address);
    }

    public User registerUser(User user){
        return userRepository.save(user);
    }

    public Address modify(Address address,Long id){
        Address toUpdate = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("addressId not found"));
        /*if (existAddress.isPresent()){

            Address toUpdate = existAddress.get();*/

        if(address.getDoorNumber() != null)
            toUpdate.setDoorNumber(address.getDoorNumber());
        if(address.getStreetName() != null)
            toUpdate.setStreetName(address.getStreetName());
        if(address.getPincode() != null)
            toUpdate.setPincode(address.getPincode());
        if(address.getCountry() != null)
            toUpdate.setCountry(address.getCountry());

        if (address.getUser() != null) {
            User user = address.getUser();
            Long userIdInAddress = user.getId();

            User userTable = userRepository.findById(userIdInAddress).orElseThrow(() -> new RuntimeException("userId Not found in User Table"));

            toUpdate.setUser(userTable);
        }

           return addressRepository.save(toUpdate);
        }

        public User put(User user,Long id){
        User toUpdateExistingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("UserId not found"));
        if(user.getUserName() != null){
            toUpdateExistingUser.setUserName(user.getUserName());
        }
        if(user.getAge() != null){
            toUpdateExistingUser.setAge(user.getAge());
        }
        if(user.getContact() != null){
            toUpdateExistingUser.setContact(user.getContact());
        }
        return userRepository.save(toUpdateExistingUser);
    }

    public void renovate(Long idno){
        Address address = addressRepository.findById(idno).orElseThrow(()-> new RuntimeException("Address Id not found in address table"));
        addressRepository.deleteById(idno);

    }

    @Transactional
    public String remove(Long id){
        Optional<User> userInUserTable = userRepository.findById(id);
        if(userInUserTable.isPresent()){

            List<Address> listOfUsersInAddress = addressRepository.findByUserId(id);
            if(! listOfUsersInAddress.isEmpty() ){
                for(Address i : listOfUsersInAddress) {
                    User user = i.getUser();
                    Long userIdInAddress = user.getId();
                    addressRepository.deleteByUserId(userIdInAddress);
                }
            }
            userRepository.deleteById(id);
            return "UserId Deleted Successfully";
        }
        else return "User Id not found";
    }

}
