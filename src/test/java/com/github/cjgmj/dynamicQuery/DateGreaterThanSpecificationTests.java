package com.github.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.modifier.filter.DateGreaterThanFilter;
import com.github.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.github.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.github.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class DateGreaterThanSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Test
	void shouldGetResultFilteredByBirthdayGreaterThan() {
		final ValueFilter<LocalDate> valueFilter = new DateGreaterThanFilter("birthday", LocalDate.of(1980, 7, 12));

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("Jane", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultFilteredByBirthdayGreaterThanOrEqual() {
		final ValueFilter<LocalDate> valueFilter = new DateGreaterThanFilter("birthday", LocalDate.of(1980, 7, 12))
				.orEqual();

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(2, dummies.size());
		assertEquals("John", dummies.get(0).getName());
		assertEquals("Jane", dummies.get(1).getName());
	}
}
