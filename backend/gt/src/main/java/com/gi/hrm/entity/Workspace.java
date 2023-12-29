package com.gi.hrm.entity;

import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gi.hrm.common.entity.CommonEntity;
import com.gi.hrm.common.entity.CommonEntityProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(value = CommonEntityProperties.COLLECTION_WORKSPACE)
public class Workspace extends CommonEntity {
	@Transient
	public static final String SEQUENCE_NAME = "workspaces_sequence";

	private String name;
	private String alias;
	private Project project;
	private List<User> member;
}
