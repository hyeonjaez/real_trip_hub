package com.ssafy.TripHub.user.dto.response;

import com.ssafy.TripHub.user.enums.Role;
import com.ssafy.TripHub.user.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String userId;
    private String email;
    private Role role;
    private Integer abilityLevel;
    private LocalDateTime createdAt;
    private Integer age;
    private Sex sex;
    private String phoneNumber;
}