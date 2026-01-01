package com.anusha.vulnerableappsec.repository;

import com.anusha.vulnerableappsec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
