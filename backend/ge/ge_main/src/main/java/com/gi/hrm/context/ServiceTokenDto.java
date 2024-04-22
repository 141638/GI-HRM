package com.gi.hrm.context;

import com.gi.hrm.security.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ServiceTokenDto {
    private String tokenId;
    private String tokenIssuer;
    private String tokenSubject;
    private String originalToken;
    private Integer userId;
    private String username;
    private String azp;
    private String serviceCd;
    private String serviceRoleCd;
    private LocalDateTime exp;
    private LocalDateTime authAt;
    private Authority authority;
}
