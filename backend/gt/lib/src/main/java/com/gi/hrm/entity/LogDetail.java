package com.gi.hrm.entity;

import com.gi.hrm.query.query.CommonEntityProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(value = CommonEntityProperties.COLLECTION_LOG_DETAIL)
public class LogDetail extends CommonEntity {
    private String name;
    private String code;
    private String content;
    private Integer priority;
    private Date estTime;
    private Date actTime;
    private User assignee;
    private User curator;
}
