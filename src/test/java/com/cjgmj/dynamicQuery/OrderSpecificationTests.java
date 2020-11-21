package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.modifier.FieldOrder;
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
