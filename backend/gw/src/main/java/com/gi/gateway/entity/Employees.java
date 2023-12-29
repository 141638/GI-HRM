package com.gi.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gi.gateway.entity.common.CommonColumnEntity;
import com.gi.gateway.entity.common.CommonEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CommonColumnEntity.EMPLOYEES)
public class Employees extends CommonEntity {
	@Column(name = CommonColumnEntity.EMAIL)
	private String email;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_group_id", nullable = false)
	@JsonProperty
	private RoleGroup roleGroups;

	@Column(name = CommonColumnEntity.USERNAME)
	private String username;

	@Column(name = CommonColumnEntity.PASSWORD)
	private String password;

	@Column(name = CommonColumnEntity.RESIGN_DATE)
	private Timestamp resignDate;

	@Column(name = CommonColumnEntity.RSPASSWORD_TOKEN)
	private String rspasswordToken;

	@Column(name = CommonColumnEntity.RSPASSWORD_EXPTIME)
	private Timestamp rspasswordExptime;
}
