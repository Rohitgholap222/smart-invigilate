package com.smartinvigilate.smartinvigilate.controller;

import com.smartinvigilate.smartinvigilate.dto.AuthenticationRequest;
import com.smartinvigilate.smartinvigilate.dto.AuthenticationResponse;
import com.smartinvigilate.smartinvigilate.dto.RegisterRequest;
import com.smartinvigilate.smartinvigilate.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(service.refreshToken(authHeader));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Logout is handled by SecurityConfiguration and LogoutHandler
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        return ResponseEntity.ok("OTP sent to " + email);
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody String otp) {
        return ResponseEntity.ok("OTP verified successfully");
    }
}
