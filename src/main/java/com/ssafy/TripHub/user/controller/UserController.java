package com.ssafy.TripHub.user.controller;

import com.ssafy.TripHub.global.auth.Authenticated;
import com.ssafy.TripHub.global.auth.AuthenticatedUser;
import com.ssafy.TripHub.user.dto.request.UserChangePasswordRequest;
import com.ssafy.TripHub.user.dto.request.UserModifyRequest;
import com.ssafy.TripHub.user.dto.request.UserRegisterRequest;
import com.ssafy.TripHub.user.dto.response.UserResponse;
import com.ssafy.TripHub.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "사용자 ID 중복 확인",
            description = "입력한 사용자 ID가 이미 존재하는지 확인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID 중복 확인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })
    @GetMapping("/check-id/{userId}")
    public ResponseEntity<Boolean> checkUserId(
            @Parameter(description = "확인할 사용자 ID", required = true)
            @PathVariable String userId) {
        boolean userIdExists = userService.isUserIdExists(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userIdExists);
    }

    @Operation(
            summary = "사용자 등록",
            description = "새로운 사용자를 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사용자 등록 성공",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 사용자 ID",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> registerUser(@Parameter(description = "사용자 등록 정보", required = true)
                                             @Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        userService.registerUser(userRegisterRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @Operation(
            summary = "사용자 로그인",
            description = "사용자가 로그인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@Parameter(description = "사용자 ID", required = true)
                                                  @RequestParam String userId,
                                                  @Parameter(description = "비밀번호", required = true)
                                                  @RequestParam String password,
                                                  @Parameter(hidden = true)
                                                  HttpSession session) {
        UserResponse userResponse = userService.loginUser(userId, password);
        if (Objects.isNull(userResponse)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        session.setAttribute("user", userResponse.getId());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "사용자 로그아웃",
            description = "현재 로그인된 사용자가 로그아웃합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })
    @Authenticated
    @GetMapping("/logout")
    public ResponseEntity<Void> logoutUser(@Parameter(hidden = true)
                                           HttpSession session) {
        if (Objects.nonNull(session)) {
            session.invalidate();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(
            summary = "사용자 정보 수정",
            description = "로그인한 사용자의 이메일과 전화번호를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사용자 정보 수정 성공",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자 정보 조회 실패",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })
    @Authenticated
    @PutMapping("/modify")
    public ResponseEntity<Void> modifyUser(@Parameter(description = "로그인한 사용자 ID", required = true)
                                           @AuthenticatedUser Long userId,
                                           @Parameter(description = "사용자 정보 수정 데이터", required = true)
                                           @Valid @RequestBody UserModifyRequest userModifyRequest) {
        userService.modifyUser(userId, userModifyRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(
            summary = "사용자 마이페이지 조회",
            description = "로그인한 사용자의 마이페이지 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "사용자 정보 조회 실패",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })
    @Authenticated
    @GetMapping("/mypage")
    public ResponseEntity<UserResponse> getMyPage(@Parameter(description = "로그인한 사용자 ID", required = true)
                                                  @AuthenticatedUser Long userId) {
        UserResponse userResponse = userService.getMyPage(userId);
        if (userResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "모든 사용자 목록 조회",
            description = "모든 사용자의 정보를 조회합니다. 관리자 권한이 필요할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })
    @Authenticated
    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(
            summary = "비밀번호 변경",
            description = "로그인한 사용자의 비밀번호를 변경합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 현재 비밀번호 또는 요청 데이터",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자 정보 조회 실패",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })
    @Authenticated
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Parameter(description = "로그인한 사용자 ID", required = true)
                                               @AuthenticatedUser Long userId,
                                               @Parameter(description = "비밀번호 변경 요청 데이터", required = true)
                                               @Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        boolean isChanged = userService.changePassword(userId, userChangePasswordRequest);
        if (isChanged) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Operation(
            summary = "비밀번호 찾기",
            description = "비밀번호를 재설정하기 위한 절차를 시작합니다. (보안상의 이유로 비밀번호 직접 반환은 권장하지 않습니다.)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 재설정 링크 이메일 전송 성공",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 사용자 ID 또는 요청 데이터",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자 정보 조회 실패",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                    content = @Content)
    })

    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@Parameter(description = "사용자 ID", required = true)
                                               @RequestParam String userId) {

        String passwordResetLink = userService.findPassword(userId);

        if (passwordResetLink == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("사용자를 찾을 수 없습니다.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("비밀번호 재설정 링크가 이메일로 전송되었습니다.");
    }
}