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

import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.filter.TextLikeFieldFilter;
import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.TextLikePredicate;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacement;
import com.cjgmj.dynamicQuery.persistence.entity.DummyEntity;

@SpringBootTest
class TextLikePredicateTests {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String QUERY_SELECT = "select d ";
	private static final String QUERY_FROM = "from dummy d ";

	private static final String LIKE = "%";

	@Test
	void shouldGetResultWithAccentMark() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh");
		final QueryPredicate queryPredicate = new TextLikePredicate();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager
				.createQuery(
						QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMark() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "oh");
		final QueryPredicate queryPredicate = new TextLikePredicate();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager
				.createQuery(
						QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkWithoutCharacterReplacement() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh");
		final QueryPredicate queryPredicate = new TextLikePredicate().defineCharactersReplacement(null);

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager
				.createQuery(
						QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacement() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "oh");
		final QueryPredicate queryPredicate = new TextLikePredicate().defineCharactersReplacement(null);

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager
				.createQuery(
						QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldNotGetResultWithAccentMarkWithoutCharacterReplacementNorNormalizeText() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "óh");
		final QueryPredicate queryPredicate = new TextLikePredicate().defineCharactersReplacement(null)
				.noNormalizeText();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager.createQuery(
				QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
						.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.FALSE).concat("'")),
				DummyEntity.class).getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.entityManager
				.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();

		assertEquals(0, queryExpect.size());
		assertEquals(0, queryResult.size());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacementNorNormalizeText() {
		final FieldFilter<String> fieldFilter = new TextLikeFieldFilter("name", "oh");
		final QueryPredicate queryPredicate = new TextLikePredicate().defineCharactersReplacement(null)
				.noNormalizeText();

		final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> criteriaQuery = criteriaBuilder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = criteriaQuery.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.entityManager.createQuery(
				QUERY_SELECT.concat(QUERY_FROM).concat(this.createWhereTextQuery())
						.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.FALSE).concat("'")),
				DummyEntity.class).getResultList();

		final Predicate predicate = queryPredicate.getPredicate(criteriaBuilder, root, fieldFilter);
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
