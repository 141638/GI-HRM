package com.gi.hrm.entity;

import com.gi.hrm.query.query.CommonEntityProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(value = CommonEntityProperties.COLLECTION_ISSUE_TYPE)
public class IssueType extends com.gi.hrm.entity.CommonEntity {
    private Integer workspaceId;
    private String name;
    private String color;
}
