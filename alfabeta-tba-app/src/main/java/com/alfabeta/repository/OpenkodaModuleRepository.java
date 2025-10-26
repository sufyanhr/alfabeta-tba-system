package com.alfabeta.repository;

import com.alfabeta.core.repository.common.UnsecuredFunctionalRepositoryWithLongId;
import com.alfabeta.core.security.HasSecurityRules;
import com.alfabeta.model.OpenkodaModule;
import org.springframework.stereotype.Repository;


@Repository
public interface OpenkodaModuleRepository extends UnsecuredFunctionalRepositoryWithLongId<OpenkodaModule>, HasSecurityRules {

    OpenkodaModule findByName(String name);
    boolean existsByName(String name);
}
