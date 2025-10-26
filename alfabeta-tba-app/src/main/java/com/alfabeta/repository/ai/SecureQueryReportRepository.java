package com.alfabeta.repository.ai;

import com.alfabeta.model.common.SearchableRepositoryMetadata;
import com.alfabeta.model.report.QueryReport;
import com.alfabeta.repository.SecureRepository;
import org.springframework.stereotype.Repository;

import static com.alfabeta.controller.common.URLConstants.QUERY_REPORT;


@Repository
@SearchableRepositoryMetadata(
        entityKey = QUERY_REPORT,
        entityClass = QueryReport.class,
        descriptionFormula = "'name: ' || COALESCE(name, '') || ' query:  ' || COALESCE(query, '') || ' orgId:' || COALESCE(CAST (organization_id as text), '')",
        searchIndexFormula = "'name: ' || lower(COALESCE(name, '')) || ' query:  ' || lower(COALESCE(query, '')) || ' orgId:' || COALESCE(CAST (organization_id as text), '')"
)
public interface SecureQueryReportRepository extends SecureRepository<QueryReport> {

}
