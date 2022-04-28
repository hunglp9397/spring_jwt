package com.hunglp.spring_security_jwt.service;

import com.hunglp.spring_security_jwt.domain.Role;
import com.hunglp.spring_security_jwt.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;



public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    List<User> getListUser();
    User getByUsername(String name);
    void addRoleToUser(String username, String roleName);



}
