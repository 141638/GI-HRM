package com.gihrm.sandservice.vuejsdemo.database.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Data
@Document(value = "gvt_store")
@EqualsAndHashCode(callSuper = false)
public class GvtStoreEntity extends CommonEntity {

    @Transient
    public static final String SEQUENCE_NAME = "gvt_store_id_seq";

    private String name;
    private String contactNumber;
    private String emailAddress;
    private String address;
    private String description;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer managerId;
    private String managerName;
    private Double rate;
    private Integer liked;
}
