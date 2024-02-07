package com.gi.gateway.entity.common;

import jakarta.persistence.Transient;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.descriptor.DateTimeUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = CommonColumnEntity.ID)
	private Integer id;

	@Column(name = CommonColumnEntity.CREATED_AT, nullable = false)
	private Timestamp createdAt;

	@Column(name = CommonColumnEntity.UPDATED_AT, nullable = false)
	private Timestamp updatedAt;

	@Column(name = CommonColumnEntity.CREATED_BY, nullable = false)
	private Integer createdBy;

	@Column(name = CommonColumnEntity.UPDATED_BY, nullable = false)
	private Integer updatedBy;

	@Column(name = CommonColumnEntity.DELETE_FLAG, nullable = false)
	private Boolean deleteFlag;

	@Transient
	private Boolean isUpdate = true;

	public void setCommonRegist(Integer employeeId) {
		this.createdAt = Timestamp.from(Instant.now());
		this.createdBy = employeeId;
		this.updatedAt = Timestamp.from(Instant.now());
		this.updatedBy = employeeId;
		this.deleteFlag = false;
	}

	public void setCommonUpdate(Integer employeeId) {
		this.updatedAt = Timestamp.from(Instant.now());
		this.updatedBy = employeeId;
	}

	public void setCommonDelete(Integer employeeId) {
		this.deleteFlag = true;
		this.setCommonUpdate(employeeId);
	}
}
