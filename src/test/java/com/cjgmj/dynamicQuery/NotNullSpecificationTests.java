package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.modifier.filter.NotNullFilter;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class NotNullSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Test
	void shouldGetResultFilteredByCustomerNotNull() {
		final ValueFilter<Void> valueFilter = new NotNullFilter("customer");

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
