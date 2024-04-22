package com.gi.hrm.database.entity;

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
@Table(name = Constants.DEPARTMENTS)
public class Departments extends CommonEntity {
	@Column(name = CommonColumnEntity.STATUS)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private Integer status;

	@Column(name = CommonColumnEntity.NAME)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private String name;

	@Column(name = CommonColumnEntity.NUMBER_MEMBER)
	@NotNull(message = Constants.VALIDATE_NOT_NULL)
	private Integer numberMember;
}
