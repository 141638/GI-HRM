package com.gi.hrm.query.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionFieldMetadata implements Serializable {
    /** serial version UID */
    @Serial
    private static final long serialVersionUID = 1L;

    /** field name(Java class) */
    private String name;

    /** field name(firestore database/ JS display) */
    private String jsName;

    /** field type(Java class) */
    private String type;

    /** field type(JS object type) */
    private String jsType;
}
