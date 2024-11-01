package com.ssafy.TripHub.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequest {
    @NotBlank(message = "User ID는 필수입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    private Integer age;

    @NotNull(message = "Sex는 필수입니다.")
    private String sex;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "유효한 전화번호 형식을 입력하세요.")
    private String phoneNumber;
}
