package com.example.apimonitoring.repository;

import com.example.apimonitoring.model.MonitoredEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {

    List<MonitoredEndpoint> findByUserIdAndIsActiveTrue(String userId);

    List<MonitoredEndpoint> findByIsActiveTrue();
}
