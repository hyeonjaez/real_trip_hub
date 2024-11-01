package com.ssafy.TripHub.user.service;

import com.ssafy.TripHub.global.utils.PasswordUtils;
import com.ssafy.TripHub.user.dto.request.UserChangePasswordRequest;
import com.ssafy.TripHub.user.dto.request.UserModifyRequest;
import com.ssafy.TripHub.user.dto.request.UserRegisterRequest;
import com.ssafy.TripHub.user.dto.response.UserResponse;
import com.ssafy.TripHub.user.dto.UserWithPassword;
import com.ssafy.TripHub.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public boolean isUserIdExists(String userId) {
        return userMapper.idCheck(userId) > 0;
    }

    public void registerUser(UserRegisterRequest userRegisterRequest) {
        String hashedPassword = PasswordUtils.encodePassword(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(hashedPassword);

        userMapper.createUser(userRegisterRequest);
    }

    @Transactional(readOnly = true)
    public UserResponse loginUser(String userId, String password) {
        UserWithPassword userWithPassword = userMapper.getUserWithPasswordByUserId(userId);
        if (userWithPassword == null) {
            return null;
        }
        boolean isPasswordMatch = PasswordUtils.matches(password, userWithPassword.getPassword());
        if (!isPasswordMatch) {

            return null;
        }

        return convertToUserResponse(userWithPassword);
    }

    public void modifyUser(Long id, UserModifyRequest userModifyRequest) {
        userMapper.updateUser(id, userModifyRequest);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        return userMapper.getAllUsers();
    }

    @Transactional(readOnly = true)
    public UserResponse getMyPage(Long id) {

        UserWithPassword userWithPassword = userMapper.getUserWithPasswordById(id);
        if (userWithPassword == null) {
            return null;
        }
        return convertToUserResponse(userWithPassword);
    }

    public boolean changePassword(Long id, UserChangePasswordRequest userChangePasswordRequest) {
        UserWithPassword userWithPassword = userMapper.getUserWithPasswordById(id);
        if (userWithPassword == null) {
            return false;
        }

        boolean isPasswordMatch = PasswordUtils.matches(userChangePasswordRequest.getOldPassword(), userWithPassword.getPassword());
        if (!isPasswordMatch) {
            return false;
        }

        String hashedNewPassword = PasswordUtils.encodePassword(userChangePasswordRequest.getNewPassword());

        userMapper.changePassword(id, hashedNewPassword);
        return true;
    }

    public String findPassword(String id) {
        UserWithPassword userWithPassword = userMapper.getUserWithPasswordByUserId(id);
        if (userWithPassword == null) {
            return null;
        }
        String imciPassword = PasswordUtils.encodePassword("dlatlzl");
        userMapper.changePassword(userWithPassword.getId(), imciPassword);

        return imciPassword;
    }


    private UserResponse convertToUserResponse(UserWithPassword user) {
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getEmail(),
                user.getRole(),
                user.getAbilityLevel(),
                user.getCreatedAt(),
                user.getAge(),
                user.getSex(),
                user.getPhoneNumber()
        );
    }


}
