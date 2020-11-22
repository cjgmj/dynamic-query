package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.modifier.filter.FalseFilter;
import com.cjgmj.dynamicQuery.modifier.filter.NotNullFilter;
import com.cjgmj.dynamicQuery.modifier.filter.NumberFilter;
import com.cjgmj.dynamicQuery.modifier.filter.TextEqualFilter;
import com.cjgmj.dynamicQuery.modifier.filter.TextLikeFilter;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class DifferentSearchsSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Test
	void shouldGetResultsWithRestrictiveSearchWithoutFilters() {
		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(null).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultsWithRestrictiveSearch() {
		final ValueFilter<Void> notNullValueFilter = new NotNullFilter("customer");
		final ValueFilter<String> textLikeValueFilter = new TextLikeFilter("surname", "doe");

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(notNullValueFilter);
		filters.add(textLikeValueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllAttributesForDummyFilteredById() {
		final ValueFilter<Number> valueFilter = new NumberFilter("id", 2L);

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

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
	void shouldGetResultsWithNonRestrictiveSearchWithoutFilters() {
		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.nonRestrictiveFilters(null).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultsWithNonRestrictiveSearch() {
		final ValueFilter<String> textLikeValueFilter = new TextLikeFilter("surname", "doe");
		final ValueFilter<Boolean> falseValueFilter = new FalseFilter("customer");

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(textLikeValueFilter);
		filters.add(falseValueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.nonRestrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(3, dummies.size());
		assertEquals("John", dummies.get(0).getName());
		assertEquals("Linus", dummies.get(1).getName());
		assertEquals("Jane", dummies.get(2).getName());
	}

	@Test
	void shouldGetResultsWithRestrictiveAndNonRestrictiveSearch() {
		final ValueFilter<String> textLikeEqualFilter = new TextEqualFilter("surname", "doe");
		final ValueFilter<String> textLikeValueFilter = new TextLikeFilter("name", "j");
		final ValueFilter<Boolean> falseValueFilter = new FalseFilter("customer");

		final List<ValueFilter<?>> filtersNonRestrictives = new ArrayList<>();
		final List<ValueFilter<?>> filtersRestrictives = new ArrayList<>();

		filtersNonRestrictives.add(textLikeEqualFilter);
		filtersRestrictives.add(textLikeValueFilter);
		filtersNonRestrictives.add(falseValueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.nonRestrictiveFilters(filtersNonRestrictives).restrictiveFilters(filtersRestrictives)
				.getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(2, dummies.size());
		assertEquals("John", dummies.get(0).getName());
		assertEquals("Jane", dummies.get(1).getName());
	}
}
