package com.ssafy.TripHub.tripinformation.service;

import com.ssafy.TripHub.tripinformation.dto.response.*;
import com.ssafy.TripHub.tripinformation.mapper.TripInformationMapper;
import com.ssafy.TripHub.tripinformation.mapper.SidoMapper;
import com.ssafy.TripHub.tripinformation.mapper.GugunMapper;
import com.ssafy.TripHub.tripinformation.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TripService {

    @Autowired
    private SidoMapper sidoMapper;

    @Autowired
    private GugunMapper gugunMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TripInformationMapper tripInformationMapper;

    /**
     * 시도 목록 조회
     */
    public List<SidoListResponse> getSidoList() {
        return sidoMapper.getSidoList();
    }

    /**
     * 특정 시도에 속한 구군 목록 조회
     */
    public List<GugunListResponse> getGugunsBysidoCode(Integer sidoCode) {
        return gugunMapper.getGugunsBysidoCode(sidoCode);
    }

    /**
     * 카테고리 목록 조회
     */
    public List<CategoryListResponse> getCategories() {
        return categoryMapper.getCategories();
    }

    /**
     * 구군과 카테고리에 따른 관광지 목록 조회
     */
    public Map<String, Object> getTripInformationByGugunAndCategory(Integer sidoCode, Integer gugunId, Integer categoryId, int page, int size) {
        int offset = (page - 1) * size;
        List<TripInformationListResponse> tripInfos = tripInformationMapper.getTripInformationByGugunAndCategory(sidoCode, gugunId, categoryId, offset, size);
        int total = tripInformationMapper.getTotalTripInformationCount(sidoCode, gugunId, categoryId);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<TripInformationListResponse> tripInfoPage = new PageImpl<>(tripInfos, pageable, total);

        return createResponseMap(tripInfoPage);
    }

    /**
     * 특정 구군에 따른 관광지 목록 조회 (카테고리 필터링 없음)
     */
    public Map<String, Object> getTripInformationByGugun(Integer sidoCode, Integer gugunId, int page, int size) {
        int offset = (page - 1) * size;
        List<TripInformationListResponse> tripInfos = tripInformationMapper.getTripInformationByGugun(sidoCode, gugunId, offset, size);
        int total = tripInformationMapper.getTotalTripInformationCountWithoutCategory(sidoCode, gugunId);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<TripInformationListResponse> tripInfoPage = new PageImpl<>(tripInfos, pageable, total);

        return createResponseMap(tripInfoPage);
    }

    /**
     * 주변 관광지 조회
     */
    public Map<String, Object> getNearbyTripInformation(Double latitude, Double longitude, int page, int size) {
        int offset = (page - 1) * size;
        List<TripInformationListResponse> tripInfos = tripInformationMapper.getNearbyTripInformation(latitude, longitude, offset, size);
        int total = tripInformationMapper.getTotalNearbyTripInformationCount(latitude, longitude);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("distance").ascending());
        Page<TripInformationListResponse> tripInfoPage = new PageImpl<>(tripInfos, pageable, total);

        return createResponseMap(tripInfoPage);
    }

    /**
     * 페이지네이션 정보를 포함한 응답 맵 생성
     */
    private Map<String, Object> createResponseMap(Page<TripInformationListResponse> tripInfoPage) {
        Map<String, Object> response = new HashMap<>();
        response.put("attractions", tripInfoPage.getContent());
        response.put("currentPage", tripInfoPage.getNumber() + 1);
        response.put("totalPages", tripInfoPage.getTotalPages());
        response.put("totalItems", tripInfoPage.getTotalElements());
        return response;
    }
}
