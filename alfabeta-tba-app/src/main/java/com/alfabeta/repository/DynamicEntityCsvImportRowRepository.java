package com.alfabeta.repository;

import com.alfabeta.core.repository.common.UnsecuredFunctionalRepositoryWithLongId;
import com.alfabeta.model.DynamicEntityCsvImportRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicEntityCsvImportRowRepository extends UnsecuredFunctionalRepositoryWithLongId<DynamicEntityCsvImportRow>{

   @Query("select uploadId from DynamicEntityCsvImportRow order by uploadId desc limit 1")
   Long findLastUploadId();
}
