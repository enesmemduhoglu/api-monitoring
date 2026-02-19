package com.example.apimonitoring.repository;

import com.example.apimonitoring.model.UptimeLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UptimeLogRepository extends ElasticsearchRepository<UptimeLog, String> {

    List<UptimeLog> findByEndpointIdOrderByTimestampDesc(Long endpointId);

    List<UptimeLog> findByEndpointIdAndIsUpFalseOrderByTimestampDesc(Long endpointId);
}