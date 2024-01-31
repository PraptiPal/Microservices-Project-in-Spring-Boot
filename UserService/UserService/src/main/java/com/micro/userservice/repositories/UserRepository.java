package com.micro.userservice.repositories;

import com.micro.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
