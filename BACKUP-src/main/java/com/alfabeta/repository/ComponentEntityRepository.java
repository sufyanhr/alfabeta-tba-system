package com.alfabeta.repository;

import com.alfabeta.model.OpenkodaModule;
import com.alfabeta.model.common.ComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ComponentEntityRepository <T extends ComponentEntity> extends JpaRepository<T, Long> {

    List<ComponentEntity> findByModule(OpenkodaModule module);

    void deleteByModule(OpenkodaModule module);
}
