package com.github.cjgmj.dynamicquery.persistence.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.github.cjgmj.dynamicquery.persistence.entity.DummyEntity;

public interface DummyRepository extends JpaRepositoryImplementation<DummyEntity, Long> {

}
