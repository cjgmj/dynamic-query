package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.filter.DateLessThanFieldFilter;
import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class DateLessThanSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Autowired
	private QuerySpecification<DummyEntity> querySpecification;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Test
	void shouldGetResultWithBirthdayLessThan() {

		final FieldFilter<LocalDate> fieldFilter = new DateLessThanFieldFilter("birthday",
				LocalDate.parse("1980-07-12", this.formatter));

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(2, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
		assertEquals("Linus", dummies.get(1).getName());
	}

	@Test
	void shouldGetResultWithBirthdayLessThanOrEqual() {

		final FieldFilter<LocalDate> fieldFilter = new DateLessThanFieldFilter("birthday",
				LocalDate.parse("1980-07-12", this.formatter)).orEqual();

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(3, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
		assertEquals("John", dummies.get(1).getName());
		assertEquals("Linus", dummies.get(2).getName());
	}
}
