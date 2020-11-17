package com.cjgmj.dynamicQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.Normalizer;
import java.util.EnumSet;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.filter.TextFieldFilter;
import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.TextPredicate;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacement;

import lombok.Getter;

@SpringBootTest
class DynamicQueryApplicationTests {

	@PersistenceContext
	private EntityManager em;

	private final String selectQuery = "select d ";
	private final String fromQuery = "from dummy d ";

	private final String like = "%";

	@Test
	void shouldGetResultWithAccentMark() {
		final FieldFilter fieldFilter = new TextFieldFilter("name", "óh");
		final QueryPredicate queryPredicate = new TextPredicate();

		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> query = builder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = query.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.em
				.createQuery(
						this.selectQuery.concat(this.fromQuery).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(builder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.em.createQuery(query.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMark() {
		final FieldFilter fieldFilter = new TextFieldFilter("name", "oh");
		final QueryPredicate queryPredicate = new TextPredicate();

		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> query = builder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = query.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.em
				.createQuery(
						this.selectQuery.concat(this.fromQuery).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(builder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.em.createQuery(query.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithAccentMarkWithoutCharacterReplacement() {
		final FieldFilter fieldFilter = new TextFieldFilter("name", "óh");
		final QueryPredicate queryPredicate = new TextPredicate().defineCharactersReplacement(null);

		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> query = builder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = query.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.em
				.createQuery(
						this.selectQuery.concat(this.fromQuery).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(builder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.em.createQuery(query.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacement() {
		final FieldFilter fieldFilter = new TextFieldFilter("name", "oh");
		final QueryPredicate queryPredicate = new TextPredicate().defineCharactersReplacement(null);

		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> query = builder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = query.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.em
				.createQuery(
						this.selectQuery.concat(this.fromQuery).concat(this.createWhereTextQuery())
								.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.TRUE).concat("'")),
						DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicate(builder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.em.createQuery(query.select(root).where(predicate)).getResultList();

		assertEquals(queryExpect.size(), queryResult.size());
		assertEquals(queryExpect.get(0).getName(), queryResult.get(0).getName());
	}

	@Test
	void shouldNotGetResultWithAccentMarkWithoutCharacterReplacementNorNormalizeText() {
		final FieldFilter fieldFilter = new TextFieldFilter("name", "óh");
		final QueryPredicate queryPredicate = new TextPredicate().defineCharactersReplacement(null).noNormalizeText();

		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> query = builder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = query.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.em.createQuery(
				this.selectQuery.concat(this.fromQuery).concat(this.createWhereTextQuery())
						.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.FALSE).concat("'")),
				DummyEntity.class).getResultList();

		final Predicate predicate = queryPredicate.getPredicate(builder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.em.createQuery(query.select(root).where(predicate)).getResultList();

		assertEquals(0, queryExpect.size());
		assertEquals(0, queryResult.size());
	}

	@Test
	void shouldGetResultWithoutAccentMarkNorCharacterReplacementNorNormalizeText() {
		final FieldFilter fieldFilter = new TextFieldFilter("name", "oh");
		final QueryPredicate queryPredicate = new TextPredicate().defineCharactersReplacement(null).noNormalizeText();

		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> query = builder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = query.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.em.createQuery(
				this.selectQuery.concat(this.fromQuery).concat(this.createWhereTextQuery())
						.concat(this.transformTextToQuery(fieldFilter.getValue(), Boolean.FALSE).concat("'")),
				DummyEntity.class).getResultList();

		final Predicate predicate = queryPredicate.getPredicate(builder, root, fieldFilter);
		final List<DummyEntity> queryResult = this.em.createQuery(query.select(root).where(predicate)).getResultList();

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

		return this.like.concat(text).concat(this.like);
	}
}

/**
 * Dummy entity to test queries
 * 
 * @author cjgmj
 *
 */

@Table(name = "dummies")
@Entity(name = "dummy")
@Getter
class DummyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String surname;
}
