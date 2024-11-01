package com.ssafy.TripHub.tripinformation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TripInformationListResponse {
	private Integer id;
	private String title;
	private Integer categoryId;
	private Integer sidoCode;
	private Integer gugunCode;
	private String firstImage;
	private String secondImage;
	private Integer mapLevel;
	private Double latitude;
	private Double longitude;
	private String address;
	private String subAddress;
	private String overview;
}
