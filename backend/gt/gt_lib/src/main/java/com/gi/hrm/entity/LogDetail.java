package com.gi.hrm.entity;

import java.util.Date;

import com.gi.hrm.common.CommonEntity;
import com.gi.hrm.common.CommonEntityProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
