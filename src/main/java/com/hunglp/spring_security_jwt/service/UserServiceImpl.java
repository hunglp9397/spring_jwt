package com.hunglp.spring_security_jwt.service;

import com.hunglp.spring_security_jwt.domain.Role;
import com.hunglp.spring_security_jwt.domain.User;
import com.hunglp.spring_security_jwt.repository.RoleRepository;
import com.hunglp.spring_security_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<User> getListUser() {
        return userRepository.findAll();
    }

    @Override
    public User getByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);


    }
}
