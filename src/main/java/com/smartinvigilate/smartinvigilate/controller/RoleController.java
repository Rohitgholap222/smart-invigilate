package com.smartinvigilate.smartinvigilate.controller;

import com.smartinvigilate.smartinvigilate.model.Role;
import com.smartinvigilate.smartinvigilate.model.User;
import com.smartinvigilate.smartinvigilate.repository.RoleRepository;
import com.smartinvigilate.smartinvigilate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleRepository.save(role));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Integer id, @RequestParam String roleName) {
        User user = userRepository.findById(id).orElseThrow();
        Role role = roleRepository.findByName(roleName).orElseThrow();
        user.setRole(role);
        return ResponseEntity.ok(userRepository.save(user));
    }
}
