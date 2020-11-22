package com.github.cjgmj.dynamicquery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.TextEqualFilter;
import com.github.cjgmj.dynamicquery.persistence.entity.DummyEntity;
import com.github.cjgmj.dynamicquery.persistence.repository.DummyRepository;
import com.github.cjgmj.dynamicquery.replacement.CharacterReplacement;
import com.github.cjgmj.dynamicquery.specification.QuerySpecification;

@SpringBootTest
public class TextEqualSpecificationTests {

	@Autowired
	private DummyRepository dummyRepository;

	@Test
	void shouldGetResultWithAccentMark() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn");

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMark() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "john");

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkWithoutCharacterReplacement() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").addListCharactersReplacement(null);

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacement() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "john").addListCharactersReplacement(null);

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldNotGetResultWithAccentMarkWithoutCharacterReplacementNorNormalizeText() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").addListCharactersReplacement(null)
				.noNormalizeText();

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(0, dummies.size());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacementNorNormalizeText() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "john").addListCharactersReplacement(null)
				.noNormalizeText();

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());

	}

	@Test
	void shouldGetResultWithAccentMarkAndBasicCharacterReplacement() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").basicReplacements();

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkIntroducingListCharactersReplacement() {

		final List<CharacterReplacement> charactersReplacement = new ArrayList<>();

		charactersReplacement.add(CharacterReplacement.O_ACUTE);

		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").emptyReplacements()
				.addListCharactersReplacement(charactersReplacement);

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkIntroducingCharacterReplacementNull() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").basicReplacements()
				.addCharacterReplacement(null);

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkIntroducingCharacterReplacement() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").emptyReplacements()
				.addCharacterReplacement(CharacterReplacement.O_ACUTE);

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkRemovingCharacterReplacementNull() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").basicReplacements()
				.removeReplacement(null);

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkRemovingCharacterReplacement() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").basicReplacements()
				.removeReplacement(CharacterReplacement.O_ACUTE);

		final List<ValueFilter<?>> filters = new ArrayList<>();

		filters.add(valueFilter);

		final Specification<DummyEntity> specification = QuerySpecification.<DummyEntity>getQuerySpecification()
				.restrictiveFilters(filters).getSpecification();

		final List<DummyEntity> dummies = this.dummyRepository.findAll(specification);

		assertEquals(1, dummies.size());
		assertEquals("John", dummies.get(0).getName());
	}

}
