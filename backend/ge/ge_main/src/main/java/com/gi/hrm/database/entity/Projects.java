package com.gi.hrm.database.entity;

import common.CommonColumnEntity;
import common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Constants.PROJECTS)
public class Projects extends CommonEntity {
    @Column(name = CommonColumnEntity.NAME)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private String name;

    @Column(name = CommonColumnEntity.TECHNOLOGY)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private String technology;

    @Column(name = CommonColumnEntity.DESCRIPTION)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private String description;

    @Column(name = CommonColumnEntity.CUSTOMER)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private String customer;

    @Column(name = CommonColumnEntity.START_DATE)
    private Timestamp starDate;

    @Column(name = CommonColumnEntity.END_DATE)
    private Timestamp endDate;

    @Column(name = CommonColumnEntity.NUMBER_MEMBER)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private Integer menberNumber;
}
