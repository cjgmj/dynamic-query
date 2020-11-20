package com.cjgmj.dynamicQuery.persistence.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;

public interface DummyRepository extends JpaRepositoryImplementation<DummyEntity, Long> {

}
