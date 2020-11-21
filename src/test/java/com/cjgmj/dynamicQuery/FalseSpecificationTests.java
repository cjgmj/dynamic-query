package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.modifier.filter.FalseFilter;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class FalseSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Test
	void shouldGetResultOfNotCustomers() {
		final ValueFilter<Boolean> valueFilter = new FalseFilter("customer");

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).buildSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("Linus", dummies.get(0).getName());
	}
}