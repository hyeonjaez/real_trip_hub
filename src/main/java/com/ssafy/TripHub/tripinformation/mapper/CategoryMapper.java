package com.ssafy.TripHub.tripinformation.mapper;

import com.ssafy.TripHub.tripinformation.dto.response.CategoryListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<CategoryListResponse> getCategories();
}
