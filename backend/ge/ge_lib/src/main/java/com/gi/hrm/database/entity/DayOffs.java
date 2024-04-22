//package com.gi.hrm.entity;
//
//import java.sql.Timestamp;
//
//import com.gi.hrm.common.CommonColumnEntity;
//import com.gi.hrm.common.Constants;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = Constants.DAY_OFFS)
//public class DayOffs extends CommonEntity {
//    @Column(name = CommonColumnEntity.START_DATE)
//    @NotNull(message = Constants.VALIDATE_NOT_NULL)
//    private Timestamp starDate;
//    @Column(name = CommonColumnEntity.END_DATE)
//    @NotNull(message = Constants.VALIDATE_NOT_NULL)
//    private Timestamp endDate;
//    @Column(name = CommonColumnEntity.STATUS)
//    @NotNull(message = Constants.VALIDATE_NOT_NULL)
//    private Integer status;
//    @Column(name = CommonColumnEntity.REASON)
//    @NotNull(message = Constants.VALIDATE_NOT_NULL)
//    private String reason;
//
//
//}
