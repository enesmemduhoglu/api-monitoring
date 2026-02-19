package com.example.apimonitoring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Document(indexName = "uptime-logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UptimeLog {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long endpointId;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Boolean)
    private boolean isUp;

    @Field(type = FieldType.Integer)
    private int statusCode;

    @Field(type = FieldType.Long)
    private long responseTimeMs;

    @Field(type = FieldType.Date)
    private Instant timestamp;
}