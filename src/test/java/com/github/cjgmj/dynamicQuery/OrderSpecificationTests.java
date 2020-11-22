package com.github.cjgmj.dynamicquery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.github.cjgmj.dynamicquery.modifier.FieldOrder;
import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.DateEqualFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.TrueFilter;
import com.github.cjgmj.dynamicquery.modifier.order.AscendingOrder;
import com.github.cjgmj.dynamicquery.modifier.order.DescendingOrder;
import com.github.cjgmj.dynamicquery.persistence.entity.DummyEntity;
import com.github.cjgmj.dynamicquery.persistence.repository.DummyRepository;
import com.github.cjgmj.dynamicquery.specification.QuerySpecification;

@SpringBootTest
public class OrderSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Test
	void shouldGetResultWithListOrdersNull() {
		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.orderBy(null).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultOrderedByAscSurnameFilteredByCustomer() {
		final AscendingOrder ascendingOrder = new AscendingOrder("surname");
		final TrueFilter trueFilter = new TrueFilter("customer");

		final List<FieldOrder> orders = new ArrayList<>();
		final List<ValueFilter<?>> filters = new ArrayList<>();

		orders.add(ascendingOrder);
		filters.add(trueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).orderBy(orders).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(2, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultOrderedByAscSurnameFilteredByCustomerAndBirthDay() {
		final AscendingOrder ascendingOrder = new AscendingOrder("surname");
		final TrueFilter trueFilter = new TrueFilter("customer");
		final DateEqualFilter dateEqualFilter = new DateEqualFilter("birthday", LocalDate.of(1980, 07, 12));

		final List<FieldOrder> orders = new ArrayList<>();
		final List<ValueFilter<?>> filters = new ArrayList<>();

		orders.add(ascendingOrder);
		filters.add(trueFilter);
		filters.add(dateEqualFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).orderBy(orders).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultOrderedByAscSurnameAndDescName() {
		final AscendingOrder ascendingOrder = new AscendingOrder("surname");
		final DescendingOrder descendingOrder = new DescendingOrder("name");

		final List<FieldOrder> orders = new ArrayList<>();

		orders.add(ascendingOrder);
		orders.add(descendingOrder);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.orderBy(orders).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

}
