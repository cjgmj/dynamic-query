package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.filter.FalseFieldFilter;
import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.filter.NotNullFieldFilter;
import com.cjgmj.dynamicQuery.filter.NumberFieldFilter;
import com.cjgmj.dynamicQuery.filter.TextLikeFieldFilter;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class DifferentSearchsTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Autowired
	private QuerySpecification<DummyEntity> querySpecification;

	@Test
	void shouldGetAllRestrictiveSearchWithoutFilters() {

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(null);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllWithRestrictiveSearch() {
		final FieldFilter<Void> notNullFieldFilter = new NotNullFieldFilter("customer");
		final FieldFilter<String> textLikeFieldFilter = new TextLikeFieldFilter("surname", "doe");

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(notNullFieldFilter);
		filters.add(textLikeFieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllAttributesForSpecificDummy() {
		final FieldFilter<Number> fieldFilter = new NumberFieldFilter("id", 2L);

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals(2L, dummies.get(0).getId());
		assertEquals("John", dummies.get(0).getName());
		assertEquals("Doe", dummies.get(0).getSurname());
		assertEquals(Boolean.TRUE, dummies.get(0).getCustomer());
		assertEquals(LocalDate.of(1980, 7, 12), dummies.get(0).getBirthday());
		assertEquals(1L, dummies.get(0).getAddress().getId());
		assertEquals("Fake street", dummies.get(0).getAddress().getStreet());
		assertEquals(123, dummies.get(0).getAddress().getNumber());
		assertEquals(1L, dummies.get(0).getAddress().getCity().getId());
		assertEquals("Mcity", dummies.get(0).getAddress().getCity().getName());

	}

	@Test
	void shouldGetAllNonRestrictiveSearchWithoutFilters() {

		final Specification<DummyEntity> specification = this.querySpecification.nonRestrictiveSearch(null);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllWithNonRestrictiveSearch() {
		final FieldFilter<String> textLikeFieldFilter = new TextLikeFieldFilter("surname", "doe");
		final FieldFilter<Boolean> falseFieldFilter = new FalseFieldFilter("customer");

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(textLikeFieldFilter);
		filters.add(falseFieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.nonRestrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(3, dummies.size());
		assertEquals("John", dummies.get(0).getName());
		assertEquals("Linus", dummies.get(1).getName());
		assertEquals("Jane", dummies.get(2).getName());
	}
}
