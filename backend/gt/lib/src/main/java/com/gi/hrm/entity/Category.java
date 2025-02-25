package com.gi.hrm.entity;

import com.gi.hrm.query.query.CommonEntityProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = CommonEntityProperties.COLLECTION_CATEGORY)
public class Category extends com.gi.hrm.entity.CommonEntity {

    @Transient
    public static final String SEQUENCE_NAME = "categories_sequence";
    private Integer workspaceId;
    private String name;
    private String color;
}
