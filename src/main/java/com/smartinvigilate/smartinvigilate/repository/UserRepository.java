package com.smartinvigilate.smartinvigilate.repository;

import com.smartinvigilate.smartinvigilate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByRole(com.smartinvigilate.smartinvigilate.model.Role role);
    java.util.List<User> findByRole(com.smartinvigilate.smartinvigilate.model.Role role);
}
