package com.github.cjgmj.dynamicquery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.DateLessThanFilter;
import com.github.cjgmj.dynamicquery.persistence.entity.DummyEntity;
import com.github.cjgmj.dynamicquery.persistence.repository.DummyRepository;
import com.github.cjgmj.dynamicquery.specification.QuerySpecification;

@SpringBootTest
public class DateLessThanSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Test
	void shouldGetResultFilteredByBirthdayLessThan() {
		final ValueFilter<LocalDate> valueFilter = new DateLessThanFilter("birthday", LocalDate.of(1980, 7, 12));

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(2, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
		assertEquals("Linus", dummies.get(1).getName());
	}

	@Test
	void shouldGetResultFilteredByBirthdayLessThanOrEqual() {
		final ValueFilter<LocalDate> valueFilter = new DateLessThanFilter("birthday", LocalDate.of(1980, 7, 12))
				.orEqual();

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(3, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
		assertEquals("John", dummies.get(1).getName());
		assertEquals("Linus", dummies.get(2).getName());
	}
}
