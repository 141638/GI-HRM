package com.gi.hrm.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gi.hrm.common.entity.CommonEntity;
import com.gi.hrm.common.entity.CommonEntityProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = CommonEntityProperties.COLLECTION_CATEGORY)
public class Category extends CommonEntity {

	@Transient
	public static final String SEQUENCE_NAME = "categories_sequence";
	private Integer workspaceId;
	private String name;
	private String color;
}
