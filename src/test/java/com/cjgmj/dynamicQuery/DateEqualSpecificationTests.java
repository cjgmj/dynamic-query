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

import com.cjgmj.dynamicQuery.filter.DateEqualFieldFilter;
import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class DateEqualSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Autowired
	private QuerySpecification<DummyEntity> querySpecification;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Test
	void shouldGetResultWithSpecificBirthday() {

		final FieldFilter<LocalDate> fieldFilter = new DateEqualFieldFilter("birthday",
				LocalDate.parse("1980-07-12", this.formatter));

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}
}
