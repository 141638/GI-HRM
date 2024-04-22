package com.gi.hrm.entity;

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
@Document(value = CommonEntityProperties.COLLECTION_LOG_HISTORY)
public class LogHistory extends CommonEntity {
	private String changelog;
	private User modifier;
}
