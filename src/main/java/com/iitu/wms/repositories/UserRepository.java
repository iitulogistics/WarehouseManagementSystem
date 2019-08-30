package com.iitu.wms.repositories;

import com.iitu.wms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    Optional<User> findByUsernameAndPassword(String name, String password);
}
