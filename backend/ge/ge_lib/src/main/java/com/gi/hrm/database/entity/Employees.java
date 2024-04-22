package com.gi.hrm.database.entity;

import com.gi.hrm.common.CommonColumnEntity;
import com.gi.hrm.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

import org.springframework.data.annotation.Version;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constants.EMPLOYEES)
public class Employees extends CommonEntity {
	@Column(name = CommonColumnEntity.EMAIL)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private String email;

	@Column(name = CommonColumnEntity.EMPLOYEE_CODE)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private String employeeCode;

	@Column(name = CommonColumnEntity.TYPE_CONTRACT)
	private Integer typeContract;

	@Column(name = CommonColumnEntity.POSITION)
	private String position;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_group_id", nullable = false)
	@JsonProperty
	private RoleGroup roleGroups;

	@Column(name = CommonColumnEntity.DEPARTMENT_ID)
	private Integer departmentId;

	@Column(name = CommonColumnEntity.STATUS)
	private Integer status;

	@Column(name = CommonColumnEntity.USERNAME)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private String username;

	@Column(name = CommonColumnEntity.PASSWORD)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private String password;

	@Column(name = CommonColumnEntity.START_DATE)
	private Timestamp startDate;

	@Column(name = CommonColumnEntity.RESIGN_DATE)
	private Timestamp resignDate;

	@Column(name = CommonColumnEntity.RSPASSWORD_TOKEN)
	private String rspasswordToken;

	@Column(name = CommonColumnEntity.RSPASSWORD_EXPTIME)
	private Timestamp rspasswordExptime;

	@Column(name = CommonColumnEntity.INSYSTEM_ROLE)
	private Integer insystemRole;

	@Version
	private Integer version;
}
