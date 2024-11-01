package com.ssafy.TripHub.user.mapper;

import com.ssafy.TripHub.user.dto.request.UserChangePasswordRequest;
import com.ssafy.TripHub.user.dto.request.UserModifyRequest;
import com.ssafy.TripHub.user.dto.request.UserRegisterRequest;
import com.ssafy.TripHub.user.dto.UserWithPassword;
import com.ssafy.TripHub.user.dto.response.UserResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int idCheck(String userId);

    void createUser(UserRegisterRequest userRegisterRequest);

    UserWithPassword getUserWithPasswordByUserId(@Param("userId") String userId);

    UserWithPassword getUserWithPasswordById(Long id);

    void updateUser(@Param("id") Long userId, @Param("userModifyRequest") UserModifyRequest userModifyRequest);

    void changePassword(Long id, String changePassword);

    List<UserResponse> getAllUsers();

}
