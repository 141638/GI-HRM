package com.gi.gateway.entity.common;

import jakarta.persistence.Transient;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@Column(name = CommonColumnEntity.UPDATED_AT, nullable = false)
	private Timestamp updatedAt;

	@Column(name = CommonColumnEntity.UPDATED_BY, nullable = false)
	private Integer updatedBy;

	@Column(name = CommonColumnEntity.DELETE_FLAG, nullable = false)
	private Boolean deleteFlag;

	@Transient
	private Boolean isUpdate = true;
}
