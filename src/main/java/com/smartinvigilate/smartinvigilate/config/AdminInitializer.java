package com.smartinvigilate.smartinvigilate.config;

import com.smartinvigilate.smartinvigilate.model.Role;
import com.smartinvigilate.smartinvigilate.model.User;
import com.smartinvigilate.smartinvigilate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final com.smartinvigilate.smartinvigilate.repository.RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            if (userRepository.findByEmail("admin@smartinvigilate.com").isEmpty()) {
                Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
                    Role role = Role.builder().name("ADMIN").build();
                    return roleRepository.save(role);
                });
                
                var admin = User.builder()
                        .firstName("Admin")
                        .lastName("System")
                        .email("admin@smartinvigilate.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(adminRole)
                        .build();
                userRepository.save(admin);
                System.out.println("Default admin account created: admin@smartinvigilate.com / admin123");
            }
        };
    }
}
