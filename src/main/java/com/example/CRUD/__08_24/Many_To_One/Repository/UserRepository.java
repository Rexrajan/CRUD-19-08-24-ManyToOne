package com.example.CRUD.__08_24.Many_To_One.Repository;

import com.example.CRUD.__08_24.Many_To_One.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User>  findByUserName(String name);
}
