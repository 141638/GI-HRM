package com.gi.hrm.database.entity;

import java.sql.Timestamp;

import com.gi.hrm.common.CommonColumnEntity;
import com.gi.hrm.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = Constants.PROFILES)
public class Profiles extends CommonEntity {
	@Column(name = CommonColumnEntity.EMPLOYEE_ID)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private Integer employeeId;

	@Column(name = CommonColumnEntity.FULL_NAME)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private String fullName;

	@Column(name = CommonColumnEntity.DATE_OF_BIRTH)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private Timestamp dateOfBirth;

	@Column(name = CommonColumnEntity.GENDER)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private Integer gender;

	@Column(name = CommonColumnEntity.ADDRESS)
	private String address;

	@Column(name = CommonColumnEntity.PHONE_NUMBER)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private String phoneNumber;

	@Column(name = CommonColumnEntity.SALARY_BASIC)
	private Integer salaryBasic;

	@Column(name = CommonColumnEntity.BANK_NAME)
	private String bankName;

	@Column(name = CommonColumnEntity.BANK_ACCOUNT)
	private String bankAccount;

	@Column(name = CommonColumnEntity.IMAGE_URL)
	private String imageUrl;

	@Column(name = CommonColumnEntity.DESCRIPTION)
	private String description;
}
