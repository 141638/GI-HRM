package com.gi.hrm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.gi.hrm.common.CommonColumnEntity;
import com.gi.hrm.common.Constants;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constants.ROLE_GROUPS)
public class RoleGroup extends CommonEntity {

    @Column(name = CommonColumnEntity.ROLE_NAME)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private String roleName;

    @Column(name = CommonColumnEntity.HR_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean hrFlag;

    @Column(name = CommonColumnEntity.LEADER_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean leaderFlag;

    @Column(name = CommonColumnEntity.PROJECT_MANAGER_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean projectManagerFlag;

    @Column(name = CommonColumnEntity.EMPLOYEE_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean employeeFlag;

    @Column(name = CommonColumnEntity.ACCOUNTANT_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean accountantFlag;

    @Column(name = CommonColumnEntity.SOURCER_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean sourcerFlag;

    @Column(name = CommonColumnEntity.RECRUITER_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean recruiterFlag;

    @Column(name = CommonColumnEntity.GUEST_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean guestFlag;

    @Column(name = CommonColumnEntity.STAFF_FLAG)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Boolean staffFlag;
}
