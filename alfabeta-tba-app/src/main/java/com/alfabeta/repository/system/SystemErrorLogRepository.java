package com.alfabeta.repository.system;

import com.alfabeta.model.system.SystemErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemErrorLogRepository extends JpaRepository<SystemErrorLog, Long> {
}
