package com.cjgmj.dynamicQuery;

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

import com.cjgmj.dynamicQuery.filter.replacement.CharacterReplacement;
import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.modifier.filter.TextEqualFilter;
import com.cjgmj.dynamicQuery.modifier.filter.TextLikeFilter;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.predicate.TextLikePredicate;

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
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").addListCharactersReplacement(null);
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
		final ValueFilter<String> valueFilter = new TextLikeFilter("name", "john").addListCharactersReplacement(null);
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
		final ValueFilter<String> valueFilter = new TextEqualFilter("name", "jóhn").addListCharactersReplacement(null)
				.noNormalizeText();
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
		final ValueFilter<String> valueFilter = new TextLikeFilter("name", "john").addListCharactersReplacement(null)
				.noNormalizeText();
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

		for (final CharacterReplacement cr : EnumSet.allOf(CharacterReplacement.class)) {
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
