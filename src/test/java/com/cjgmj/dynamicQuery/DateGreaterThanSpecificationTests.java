package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.filter.DateGreaterThanFieldFilter;
import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class DateGreaterThanSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Autowired
	private QuerySpecification<DummyEntity> querySpecification;

	@Test
	void shouldGetResultWithBirthdayGreaterThan() {

		final FieldFilter<LocalDate> fieldFilter = new DateGreaterThanFieldFilter("birthday",
				LocalDate.of(1980, 7, 12));

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("Jane", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithBirthdayGreaterThanOrEqual() {

		final FieldFilter<LocalDate> fieldFilter = new DateGreaterThanFieldFilter("birthday", LocalDate.of(1980, 7, 12))
				.orEqual();

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(2, dummies.size());
		assertEquals("John", dummies.get(0).getName());
		assertEquals("Jane", dummies.get(1).getName());
	}
}
