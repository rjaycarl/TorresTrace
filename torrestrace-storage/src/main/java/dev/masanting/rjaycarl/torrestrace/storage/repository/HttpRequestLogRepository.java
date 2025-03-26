package dev.masanting.rjaycarl.torrestrace.storage.repository;

import dev.masanting.rjaycarl.torrestrace.storage.entity.HttpRequestLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Repository
public interface HttpRequestLogRepository extends JpaRepository<HttpRequestLogEntity, UUID> {
}