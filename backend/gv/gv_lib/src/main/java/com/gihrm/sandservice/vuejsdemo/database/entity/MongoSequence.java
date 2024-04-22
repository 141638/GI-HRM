package com.gihrm.sandservice.vuejsdemo.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "database_sequence")
public class MongoSequence {
    @Id
    private String id;
    private int seq;
}
