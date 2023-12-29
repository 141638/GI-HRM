package com.gi.hrm.entity;

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
@Table(name = Constants.EQUIPMENTS)
public class Equipments extends CommonEntity {
    @Column(name = CommonColumnEntity.NAME)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private String name;
    @Column(name = CommonColumnEntity.SERIAL_NUMBER)
    @NotNull(message = Constants.VALIDATE_NOT_NULL)
    private String serialNumber;
    @Column(name = CommonColumnEntity.DESCRIPTION)
    private String Description;
    @Column(name = CommonColumnEntity.IMPORT_DATE)
    private Timestamp importDate;
    @Column(name = CommonColumnEntity.STATUS)
    private Integer status;
    @Column(name = CommonColumnEntity.CATEGORY)
    private Integer category;
    @Column(name = CommonColumnEntity.VENDOR)
    private String vendor;
    private String warrantyTime;

}
