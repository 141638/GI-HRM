package com.gi.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.gi.gateway.entity.common.CommonColumnEntity;
import com.gi.gateway.entity.common.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CommonColumnEntity.ROLE_GROUPS)
public class RoleGroup extends CommonEntity {

	@Column(name = CommonColumnEntity.ROLE_NAME)
	private String roleName;

	@Column(name = CommonColumnEntity.HR_FLAG)
	private Boolean hrFlag;

	@Column(name = CommonColumnEntity.LEADER_FLAG)
	private Boolean leaderFlag;

	@Column(name = CommonColumnEntity.PROJECT_MANAGER_FLAG)
	private Boolean projectManagerFlag;

	@Column(name = CommonColumnEntity.EMPLOYEE_FLAG)
	private Boolean employeeFlag;

	@Column(name = CommonColumnEntity.ACCOUNTANT_FLAG)
	private Boolean accountantFlag;

	@Column(name = CommonColumnEntity.SOURCER_FLAG)
	private Boolean sourcerFlag;

	@Column(name = CommonColumnEntity.RECRUITER_FLAG)
	private Boolean recruiterFlag;

	@Column(name = CommonColumnEntity.GUEST_FLAG)
	private Boolean guestFlag;

	@Column(name = CommonColumnEntity.STAFF_FLAG)
	private Boolean staffFlag;
}
