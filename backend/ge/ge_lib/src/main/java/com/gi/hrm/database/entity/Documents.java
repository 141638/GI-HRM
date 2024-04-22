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
//@Table(name = Constants.DOCUMENTS)
//public class Documents extends CommonEntity{
//    @Column(name = CommonColumnEntity.PROJECT_ID)
//    @NotNull(message = Constants.VALIDATE_NOT_NULL)
//    private Integer projectId;
//    @Column(name = CommonColumnEntity.DESCRIPTION)
//    private String description;
//    @Column(name = CommonColumnEntity.CONTENT)
//    private String content;
//
//
//}
