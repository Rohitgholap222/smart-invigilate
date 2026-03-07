package com.smartinvigilate.smartinvigilate.config;

import com.smartinvigilate.smartinvigilate.model.Role;
import com.smartinvigilate.smartinvigilate.model.User;
import com.smartinvigilate.smartinvigilate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            if (!userRepository.existsByRole(Role.ADMIN)) {
                var admin = User.builder()
                        .firstName("Admin")
                        .lastName("System")
                        .email("admin@smartinvigilate.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("Default admin account created: admin@smartinvigilate.com / admin123");
            }
        };
    }
}
