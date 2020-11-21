package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.modifier.FieldOrder;
import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.modifier.filter.DateEqualFilter;
import com.cjgmj.dynamicQuery.modifier.filter.TrueFilter;
import com.cjgmj.dynamicQuery.modifier.order.AscendingOrder;
import com.cjgmj.dynamicQuery.modifier.order.DescendingOrder;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class OrderSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Test
	void shouldGetAllWithListOrdersNull() {
		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.orderBy(null).buildSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllOrderedByAscSurnameFilteredByCustomer() {
		final AscendingOrder ascendingOrder = new AscendingOrder("surname");
		final TrueFilter trueFilter = new TrueFilter("customer");

		final List<FieldOrder> orders = new ArrayList<>();
		final List<ValueFilter<?>> filters = new ArrayList<>();

		orders.add(ascendingOrder);
		filters.add(trueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).orderBy(orders).buildSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(2, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllOrderedByAscSurnameFilteredByCustomerAndBirthDay() {
		final AscendingOrder ascendingOrder = new AscendingOrder("surname");
		final TrueFilter trueFilter = new TrueFilter("customer");
		final DateEqualFilter dateEqualFilter = new DateEqualFilter("birthday", LocalDate.of(1980, 07, 12));

		final List<FieldOrder> orders = new ArrayList<>();
		final List<ValueFilter<?>> filters = new ArrayList<>();

		orders.add(ascendingOrder);
		filters.add(trueFilter);
		filters.add(dateEqualFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).orderBy(orders).buildSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllOrderedByAscSurnameAndDescName() {
		final AscendingOrder ascendingOrder = new AscendingOrder("surname");
		final DescendingOrder descendingOrder = new DescendingOrder("name");

		final List<FieldOrder> orders = new ArrayList<>();

		orders.add(ascendingOrder);
		orders.add(descendingOrder);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.orderBy(orders).buildSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

}
