package com.gihrm.sandservice.vuejsdemo.dto.response;

import lombok.Data;

import java.time.LocalTime;

@Data
public class GvtStoreListResponseDto {
    private Integer id;
    private String name;
    private String description;
    private String contactNumber;
    private String emailAddress;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer status;
}
