package com.gi.hrm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonEntity {

    @Id
    private Integer id;

    private Date createdAt;

    private Date updatedAt;

    public void setCommonRegist(Integer userId) {
        this.createdAt = new Date();
        this.updatedAt = this.createdAt;
    }

    public void setCommonUpdate(Integer userId) {
        this.updatedAt = new Date();
    }

    public void setCommonDelete(Integer userId) {
        this.setCommonUpdate(userId);
    }
}
