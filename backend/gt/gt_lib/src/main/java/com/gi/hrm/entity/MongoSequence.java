package com.gi.hrm.entity;

import com.gi.hrm.common.CommonEntityProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = CommonEntityProperties.COLLECTION_DATABASE_SEQUENCE)
public class MongoSequence {
	@Id
	private String id;

	private int seq;
}
