package com.amusementpark.service;

import com.amusementpark.model.Role;
import com.amusementpark.model.User;
import com.amusementpark.repository.RoleRepository;
import com.amusementpark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, 
                      RoleRepository roleRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setEnabled(true);

        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseGet(() -> {
                Role role = new Role("ROLE_USER");
                return roleRepository.save(role);
            });
        user.addRole(userRole);

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

