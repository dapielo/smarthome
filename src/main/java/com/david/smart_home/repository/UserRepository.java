package com.david.smart_home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.david.smart_home.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{

}
