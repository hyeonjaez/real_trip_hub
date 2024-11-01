package com.ssafy.TripHub.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserChangePasswordRequest {
    @NotBlank(message = "기존 비밀번호는 필수입니다.")
    private String oldPassword;

    @NotBlank(message = "변경 비밀번호는 필수입니다.")
    private String newPassword;
}
