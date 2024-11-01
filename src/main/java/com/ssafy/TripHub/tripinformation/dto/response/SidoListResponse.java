package com.ssafy.TripHub.tripinformation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class SidoListResponse {
    private Integer id;
    private String sidoCode;
    private String sidoName;
}
