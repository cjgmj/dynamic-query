package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.filter.FalseFieldFilter;
import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.filter.NotNullFieldFilter;
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
	void shouldGetAllWithoutFilters() {

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(null);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(4, dummies.size());
		assertEquals("Joe", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllWithRestrictiveSearch() {
		final FieldFilter<Void> notNullfieldFilter = new NotNullFieldFilter("customer");
		final FieldFilter<String> textLikefieldFilter = new TextLikeFieldFilter("surname", "doe");

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(notNullfieldFilter);
		filters.add(textLikefieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetAllWithNonRestrictiveSearch() {
		final FieldFilter<String> textLikefieldFilter = new TextLikeFieldFilter("surname", "doe");
		final FieldFilter<Boolean> falsefieldFilter = new FalseFieldFilter("customer");

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(textLikefieldFilter);
		filters.add(falsefieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.nonRestrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(3, dummies.size());
		assertEquals("John", dummies.get(0).getName());
		assertEquals("Linus", dummies.get(1).getName());
		assertEquals("Jane", dummies.get(2).getName());
	}
}
