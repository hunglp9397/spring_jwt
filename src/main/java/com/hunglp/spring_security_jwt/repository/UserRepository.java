package com.hunglp.spring_security_jwt.repository;

import com.hunglp.spring_security_jwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);
}
