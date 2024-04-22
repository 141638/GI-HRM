package com.gihrm.sandservice.vuejsdemo.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class CommonEntity {
    @Id
    private Integer id;

    private LocalDateTime createdAt;

    private Integer createdBy;

    private LocalDateTime updatedAt;

    private Integer updatedBy;

    private Boolean deleteFlag;

    public void setCommonRegist(Integer userId) {
        this.createdAt = LocalDateTime.now();
        this.createdBy = userId;
        this.updatedAt = this.createdAt;
        this.updatedBy = this.createdBy;
        this.deleteFlag = false;
    }

    public void setCommonUpdate(Integer userId) {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = userId;
    }

    public void setCommonDelete(Integer userId) {
        this.deleteFlag = true;
        this.setCommonUpdate(userId);
    }
}
