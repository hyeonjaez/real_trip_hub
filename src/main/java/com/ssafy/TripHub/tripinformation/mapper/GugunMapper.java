package com.ssafy.TripHub.tripinformation.mapper;

import com.ssafy.TripHub.tripinformation.dto.response.GugunListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GugunMapper {
    List<GugunListResponse> getGugunsBysidoCode(@Param("sidoCode") Integer sidoCode);
}
