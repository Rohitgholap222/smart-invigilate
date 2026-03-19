package com.smartinvigilate.smartinvigilate.service;

import com.smartinvigilate.smartinvigilate.dto.AuthenticationRequest;
import com.smartinvigilate.smartinvigilate.dto.AuthenticationResponse;
import com.smartinvigilate.smartinvigilate.dto.RegisterRequest;
import java.util.List;
import com.smartinvigilate.smartinvigilate.model.Role;
import com.smartinvigilate.smartinvigilate.model.User;
import com.smartinvigilate.smartinvigilate.repository.UserRepository;
import com.smartinvigilate.smartinvigilate.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void addStudent(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }
        var user = User.builder()
                .studentId(request.getStudentId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .rollNumber(request.getRollNumber())
                .registrationNumber(request.getRegistrationNumber())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .department(request.getDepartment())
                .course(request.getCourse())
                .semester(request.getSemester())
                .year(request.getYear())
                .collegeName(request.getCollegeName())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .role(Role.STUDENT)
                .build();
        repository.save(user);
    }

    public void addStudents(List<RegisterRequest> requests) {
        for (RegisterRequest request : requests) {
            addStudent(request);
        }
    }
}
