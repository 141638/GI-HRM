package com.gi.hrm.entity;

import com.gi.hrm.query.query.CommonEntityProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
    private List<User> member = new ArrayList<>();
}
