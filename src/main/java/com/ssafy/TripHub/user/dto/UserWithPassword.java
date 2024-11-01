package com.ssafy.TripHub.user.dto;

import com.ssafy.TripHub.user.enums.Role;
import com.ssafy.TripHub.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithPassword {
    private Long id;
    private String userId;
    private String email;
    private Role role;
    private Integer abilityLevel;
    private LocalDateTime createdAt;
    private Integer age;
    private Sex sex;
    private String phoneNumber;
    private String password;
}