package com.alfabeta.repository;

import com.alfabeta.model.OpenkodaModule;
import com.alfabeta.model.common.SearchableRepositoryMetadata;
import org.springframework.stereotype.Repository;

import static com.alfabeta.controller.common.URLConstants.MODULE;


@Repository
@SearchableRepositoryMetadata(
        entityKey = MODULE,
        descriptionFormula = "(''||name)",
        entityClass = OpenkodaModule.class
)
public interface SecureOpenkodaModuleRepository extends SecureRepository<OpenkodaModule> {

}
