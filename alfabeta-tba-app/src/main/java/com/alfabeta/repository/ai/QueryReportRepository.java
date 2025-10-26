package com.alfabeta.repository.ai;


import com.alfabeta.core.repository.common.UnsecuredFunctionalRepositoryWithLongId;
import com.alfabeta.core.security.HasSecurityRules;
import com.alfabeta.model.report.QueryReport;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryReportRepository extends UnsecuredFunctionalRepositoryWithLongId<QueryReport>, HasSecurityRules {
}
