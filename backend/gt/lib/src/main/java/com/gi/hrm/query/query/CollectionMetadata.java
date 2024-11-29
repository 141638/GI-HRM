package com.gi.hrm.query.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CollectionMetadata {

    /** collection name */
    private String name;

    /** fields metadata */
    private List<CollectionFieldMetadata> fields;
}
