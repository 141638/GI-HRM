package com.gihrm.sandservice.vuejsdemo.dto.response;

import lombok.Data;

@Data
public class GvtStoreListResponseDto {
    private Integer id;
    private String name;
    private String description;
    private String contactNumber;
    private String emailAddress;
    private String openTime;
    private String closeTime;
    private Integer status;
}
