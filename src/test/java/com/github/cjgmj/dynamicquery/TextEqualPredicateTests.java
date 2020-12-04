package com.github.cjgmj.dynamicquery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.Normalizer;
import java.util.EnumSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.TextEqualFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.TextLikeFilter;
import com.github.cjgmj.dynamicquery.modifier.text.configuration.TextFilterConfiguration;
import com.github.cjgmj.dynamicquery.modifier.text.configuration.TextReplacement;
import com.github.cjgmj.dynamicquery.persistence.entity.DummyEntity;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;
import com.github.cjgmj.dynamicquery.predicate.TextLikePredicate;
import com.github.cjgmj.dynamicquery.replacement.BasicCharacterReplacement;
import com.github.cjgmj.dynamicquery.replacement.CharacterReplacement;

@SpringBootTest
class TextEqualPredicateTests {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String QUERY_SELECT = "select d ";
	private static final String QUERY_FROM = "from dummy d ";

	private static final String LIKE = "%";

	@Test
	void shouldGetResultWithAccentMark() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn");
		final QueryPredicate queryPredicate = new TextLikePredicate();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager
				.createQuery(
						QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(valueFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, valueFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMark() {
		final ValueFilter<String> valueFilter = new TextLikeFilter("name", "john");
		final QueryPredicate queryPredicate = new TextLikePredicate();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager
				.createQuery(
						QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(valueFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, valueFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkWithoutCharacterReplacement() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").customizeConfiguration(
				TextFilterConfiguration.normalizeTextWithTextReplacement(TextReplacement.customizeReplacement()));
		final QueryPredicate queryPredicate = new TextLikePredicate();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager
				.createQuery(
						QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(valueFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, valueFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacement() {
		final ValueFilter<String> valueFilter = new TextLikeFilter("name", "john").customizeConfiguration(
				TextFilterConfiguration.normalizeTextWithTextReplacement(TextReplacement.customizeReplacement()));
		final QueryPredicate queryPredicate = new TextLikePredicate();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager
				.createQuery(
						QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(valueFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, valueFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldNotGetResultWithAccentMarkWithoutCharacterReplacementNorNormalizeText() {
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").customizeConfiguration(
				TextFilterConfiguration.noNormalizeTextWithTextReplacement(TextReplacement.customizeReplacement()));
		final QueryPredicate queryPredicate = new TextLikePredicate();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager.createQuery(
				QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
						.concat(this.transformTextToQuery(valueFilter.getValue(), Boolean.FALSE).concat("'")),
				DummyEntity.class).getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, valueFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(0, queryExpect.size());
		assertEquals(0, queryResult.size());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacementNorNormalizeText() {
		final ValueFilter<String> valueFilter = new TextLikeFilter("name", "john").customizeConfiguration(
				TextFilterConfiguration.noNormalizeTextWithTextReplacement(TextReplacement.customizeReplacement()));
		final QueryPredicate queryPredicate = new TextLikePredicate();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager.createQuery(
				QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
						.concat(this.transformTextToQuery(valueFilter.getValue(), Boolean.FALSE).concat("'")),
				DummyEntity.class).getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, valueFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	private String createWhereTextQuery() {
		String where = "lower(d.name)";

		for (final CharacterReplacement cr : EnumSet.allOf(BasicCharacterReplacement.class)) {
			where = "replace(".concat(where).concat(",'").concat(cr.getOldCharacter()).concat("', '")
					.concat(cr.getNewCharacter()).concat("')");
		}

		where = "where ".concat(where).concat(" like '");

		return where;
	}

	private String transformTextToQuery(String text, Boolean normalize) {
		if (normalize) {
			text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		return LIKE.concat(text).concat(LIKE);
	}
}
