package com.ssafy.TripHub.tripinformation.mapper;

import com.ssafy.TripHub.tripinformation.dto.response.TripInformationListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripInformationMapper {
    // 구군과 카테고리에 따른 관광지 목록 조회
    List<TripInformationListResponse> getTripInformationByGugunAndCategory(
            @Param("sidoCode") Integer sidoCode,
            @Param("gugunCode") Integer gugunCode,
            @Param("categoryId") Integer categoryId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    // 특정 구군에 따른 관광지 목록 조회 (카테고리 필터링 없음)
    List<TripInformationListResponse> getTripInformationByGugun(
            @Param("sidoCode") Integer sidoCode,
            @Param("gugunCode") Integer gugunCode,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    // 주변 관광지 조회
    List<TripInformationListResponse> getNearbyTripInformation(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    // 총 관광지 수 조회 (카테고리 포함)
    int getTotalTripInformationCount(
            @Param("sidoCode") Integer sidoCode,
            @Param("gugunCode") Integer gugunCode,
            @Param("categoryId") Integer categoryId
    );

    // 총 관광지 수 조회 (카테고리 제외)
    int getTotalTripInformationCountWithoutCategory(
            @Param("sidoCode") Integer sidoCode,
            @Param("gugunCode") Integer gugunCode
    );

    // 총 주변 관광지 수 조회
    int getTotalNearbyTripInformationCount(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude
    );
}
