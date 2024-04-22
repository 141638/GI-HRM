package com.gihrm.sandservice.vuejsdemo.dto.request;


import com.gihrm.sandservice.vuejsdemo.auth.Authority;

import java.time.LocalDateTime;

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

    public ServiceTokenDto() {
    }

    public ServiceTokenDto(String tokenId, String tokenIssuer, String tokenSubject, String originalToken, Integer userId, String username, String azp,
        String serviceCd, String serviceRoleCd, LocalDateTime exp, LocalDateTime authAt, Authority authority) {
        this.tokenId = tokenId;
        this.tokenIssuer = tokenIssuer;
        this.tokenSubject = tokenSubject;
        this.originalToken = originalToken;
        this.userId = userId;
        this.username = username;
        this.azp = azp;
        this.serviceCd = serviceCd;
        this.serviceRoleCd = serviceRoleCd;
        this.exp = exp;
        this.authAt = authAt;
        this.authority = authority;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSubject() {
        return tokenSubject;
    }

    public void setTokenSubject(String tokenSubject) {
        this.tokenSubject = tokenSubject;
    }

    public String getOriginalToken() {
        return originalToken;
    }

    public void setOriginalToken(String originalToken) {
        this.originalToken = originalToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAzp() {
        return azp;
    }

    public void setAzp(String azp) {
        this.azp = azp;
    }

    public String getServiceCd() {
        return serviceCd;
    }

    public void setServiceCd(String serviceCd) {
        this.serviceCd = serviceCd;
    }

    public String getServiceRoleCd() {
        return serviceRoleCd;
    }

    public void setServiceRoleCd(String serviceRoleCd) {
        this.serviceRoleCd = serviceRoleCd;
    }

    public LocalDateTime getExp() {
        return exp;
    }

    public void setExp(LocalDateTime exp) {
        this.exp = exp;
    }

    public LocalDateTime getAuthAt() {
        return authAt;
    }

    public void setAuthAt(LocalDateTime authAt) {
        this.authAt = authAt;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public static class ServiceTokenDtoBuilder{
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

        public ServiceTokenDtoBuilder tokenId(String tokenId){
            this.tokenId = tokenId;
            return this;
        }

        public ServiceTokenDtoBuilder tokenIssuer(String tokenIssuer){
            this.tokenIssuer = tokenIssuer;
            return this;
        }

        public ServiceTokenDtoBuilder tokenSubject(String tokenSubject) {
            this.tokenSubject = tokenSubject;
            return this;
        }

        public ServiceTokenDtoBuilder originalToken(String originalToken) {
            this.originalToken = originalToken;
            return this;
        }

        public ServiceTokenDtoBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public ServiceTokenDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ServiceTokenDtoBuilder azp(String azp) {
            this.azp = azp;
            return this;
        }

        public ServiceTokenDtoBuilder serviceCd(String serviceCd) {
            this.serviceCd = serviceCd;
            return this;
        }

        public ServiceTokenDtoBuilder serviceRoleCd(String serviceRoleCd) {
            this.serviceRoleCd = serviceRoleCd;
            return this;
        }

        public ServiceTokenDtoBuilder exp(LocalDateTime exp) {
            this.exp = exp;
            return this;
        }

        public ServiceTokenDtoBuilder authAt(LocalDateTime authAt) {
            this.authAt = authAt;
            return this;
        }

        public ServiceTokenDtoBuilder authority(Authority authority) {
            this.authority = authority;
            return this;
        }

        public ServiceTokenDto build(){
            return new ServiceTokenDto(this.tokenId, this.tokenIssuer, this.tokenSubject, this.originalToken, this.userId, this.username,
                this.azp, this.serviceCd, this.serviceRoleCd, this.exp, this.authAt, this.authority);
        }
    }

    public static ServiceTokenDtoBuilder builder(){
        return new ServiceTokenDtoBuilder();
    }
}
