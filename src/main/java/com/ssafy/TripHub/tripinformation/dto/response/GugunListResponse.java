package com.ssafy.TripHub.tripinformation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GugunListResponse {
    private Integer id;
    private String sidoCode;
    private String gugunName;
    private String gugunCode;
}
