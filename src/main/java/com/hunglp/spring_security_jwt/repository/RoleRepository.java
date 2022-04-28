package com.hunglp.spring_security_jwt.repository;

import com.hunglp.spring_security_jwt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
