package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.filter.TextLikeFieldFilter;
import com.cjgmj.dynamicQuery.filter.replacement.CharacterReplacement;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.persistence.repository.DummyRepository;
import com.cjgmj.dynamicQuery.specification.QuerySpecification;

@SpringBootTest
public class TextLikeSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Autowired
	private QuerySpecification<DummyEntity> querySpecification;

	@Test
	void shouldGetResultWithAccentMark() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh");

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMark() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "oh");

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkWithoutCharacterReplacement() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh")
				.addListCharactersReplacement(null);

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacement() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "oh")
				.addListCharactersReplacement(null);

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldNotGetResultWithAccentMarkWithoutCharacterReplacementNorNormalizeText() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh").addListCharactersReplacement(null)
				.noNormalizeText();

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(0, dummies.size());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacementNorNormalizeText() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "oh").addListCharactersReplacement(null)
				.noNormalizeText();

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());

	}

	@Test
	void shouldGetResultWithAccentMarkIntroducingListCharacterReplacementManually() {

		final List<CharacterReplacement> charactersReplacement = new ArrayList<>();

		charactersReplacement.add(CharacterReplacement.O_ACUTE);

		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh").emptyReplacements()
				.addListCharactersReplacement(charactersReplacement);

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkIntroducingCharacterReplacementManually() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh").emptyReplacements()
				.addCharacterReplacement(CharacterReplacement.O_ACUTE);

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkBasicCharacterReplacement() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh").basicReplacements();

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkIntroducingCharacterReplacementNull() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh").basicReplacements()
				.addCharacterReplacement(null);

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkRemovingCharacterReplacement() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh").basicReplacements()
				.removeReplacement(CharacterReplacement.O_ACUTE);

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkRemovingCharacterReplacementNull() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh").basicReplacements()
				.removeReplacement(null);

		final List<FieldFilter<?>> filters = new ArrayList<>();

		filters.add(fieldFilter);

		final Specification<DummyEntity> specification = this.querySpecification.restrictiveSearch(filters);

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

}
