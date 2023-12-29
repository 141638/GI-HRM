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
@Table(name = Constants.BOOKING_ROOMS)
public class BookingRooms extends CommonEntity {
    @Column(name = CommonColumnEntity.START_DATE)
    private Timestamp starDate;
    @Column(name = CommonColumnEntity.END_DATE)
    private Timestamp endDate;
    @Column(name = CommonColumnEntity.REASON)
    private String reason;
    @Column(name = CommonColumnEntity.STATUS)
    private String status;
    @Column(name = CommonColumnEntity.ROOMS_ID)
    private Integer roomId;

}
