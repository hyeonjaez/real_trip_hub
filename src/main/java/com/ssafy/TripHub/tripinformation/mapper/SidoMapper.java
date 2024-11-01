package com.ssafy.TripHub.tripinformation.mapper;

import com.ssafy.TripHub.tripinformation.dto.response.SidoListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SidoMapper {
    List<SidoListResponse> getSidoList();
}
