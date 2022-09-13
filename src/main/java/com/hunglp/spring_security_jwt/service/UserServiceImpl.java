package com.hunglp.spring_security_jwt.service;

import com.hunglp.spring_security_jwt.constant.CommonVar;
import com.hunglp.spring_security_jwt.domain.CustomUser;
import com.hunglp.spring_security_jwt.domain.Role;
import com.hunglp.spring_security_jwt.domain.User;
import com.hunglp.spring_security_jwt.repository.RoleRepository;
import com.hunglp.spring_security_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        if(this.redisTemplate.opsForHash().get(username, "user") != null){
            user = (User)this.redisTemplate.opsForHash().get(username, "user");
        }else{
            user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not exists in DB");
            }
            this.redisTemplate.opsForHash().put(username, "user", user);
            this.redisTemplate.expire(username, CommonVar.effectiveAccessToken, TimeUnit.MILLISECONDS);
        }

        return new CustomUser(user);


    }
}
