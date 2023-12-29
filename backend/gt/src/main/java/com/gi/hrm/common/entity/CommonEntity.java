package com.gi.hrm.common.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonEntity {

	@Id
	private Integer id;

	private Date createdAt;

	private Integer createdBy;

	private Date updatedAt;

	private Integer updatedBy;

	private Boolean deleteFlag;

	public void setCommonRegist(Integer userId) {
		this.createdAt = new Date();
		this.createdBy = userId;
		this.updatedAt = this.createdAt;
		this.updatedBy = this.createdBy;
		this.deleteFlag = false;
	}

	public void setCommonUpdate(Integer userId) {
		this.updatedAt = new Date();
		this.updatedBy = userId;
	}

	public void setCommonDelete(Integer userId) {
		this.deleteFlag = true;
		this.setCommonUpdate(userId);
	}
}
