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
@Document(value = CommonEntityProperties.COLLECTION_LOG_HISTORY)
public class LogHistory extends CommonEntity {
    private String changelog;
    private User modifier;
}
