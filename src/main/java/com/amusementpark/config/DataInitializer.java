package com.amusementpark.config;

import com.amusementpark.model.Role;
import com.amusementpark.model.User;
import com.amusementpark.repository.RoleRepository;
import com.amusementpark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, 
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }

        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@park.ru");
            admin.setEnabled(true);

            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            roles.add(roleRepository.findByName("ROLE_USER").orElseThrow());
            admin.setRoles(roles);

            userRepository.save(admin);
        }
    }
}

