package com.ssafy.TripHub.tripinformation.controller;

import com.ssafy.TripHub.tripinformation.dto.response.*;
import com.ssafy.TripHub.tripinformation.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trip")
public class TripInformationController {

    @Autowired
    private TripService tripService;

    /**
     * 시도 목록 조회
     */
    @Operation(summary = "시도 목록 조회", description = "모든 시도 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시도 목록 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SidoListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/sidos")
    public List<SidoListResponse> getSidoList() {
        return tripService.getSidoList();
    }

    /**
     * 특정 시도에 속한 구군 목록 조회
     */
    @Operation(summary = "특정 시도에 속한 구군 목록 조회", description = "시도 코드를 기반으로 구군 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구군 목록 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GugunListResponse.class))}),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 시도 코드"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/guguns")
    public List<GugunListResponse> getGugunsBySido(@RequestParam("sidoCode") Integer sidoCode) {
        return tripService.getGugunsBysidoCode(sidoCode);
    }

    /**
     * 카테고리 목록 조회
     */
    @Operation(summary = "카테고리 목록 조회", description = "모든 사용 가능한 카테고리 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/categories")
    public List<CategoryListResponse> getCategories() {
        return tripService.getCategories();
    }

    /**
     * 구군과 카테고리에 따른 관광지 목록 조회
     */
    @Operation(summary = "구군과 카테고리에 따른 관광지 목록 조회", description = "구군과 카테고리로 필터링된 관광지 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관광지 목록 조회 성공",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 매개변수"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/tripinformation")
    public Map<String, Object> getTripInformationByGugunAndCategory(
            @RequestParam("sidoCode") Integer sidoCode,
            @RequestParam("gugunCode") Integer gugunCode,
            @RequestParam(value = "categoryId", required = false, defaultValue = "0") Integer categoryId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return tripService.getTripInformationByGugunAndCategory(sidoCode, gugunCode, categoryId, page, size);
    }

    /**
     * 특정 구군에 따른 관광지 목록 조회 (카테고리 필터링 없음)
     */
    @Operation(summary = "특정 구군에 따른 관광지 목록 조회", description = "카테고리 필터링 없이 구군으로 필터링된 관광지 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관광지 목록 조회 성공",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 매개변수"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/tripinformation/gugun")
    public Map<String, Object> getTripInformationByGugun(
            @RequestParam("sidoCode") Integer sidoCode,
            @RequestParam("gugunCode") Integer gugunCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return tripService.getTripInformationByGugun(sidoCode, gugunCode, page, size);
    }

    /**
     * 주변 관광지 조회
     */
    @Operation(summary = "주변 관광지 조회", description = "지정된 위치 주변의 관광지 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주변 관광지 목록 조회 성공",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 위도 또는 경도"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/tripinformation/nearby")
    public Map<String, Object> getNearbyTripInformation(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return tripService.getNearbyTripInformation(latitude, longitude, page, size);
    }
}
