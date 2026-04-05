package com.smartinvigilate.smartinvigilate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String studentId;
    private String firstName;
    private String lastName;
    private String rollNumber;
    private String registrationNumber;
    private String email;
    private String phoneNumber;
    private String password;
    private String department;
    private String course;
    private String semester;
    private String year;
    private String collegeName;
    private Boolean isActive;
    private String role;
}
