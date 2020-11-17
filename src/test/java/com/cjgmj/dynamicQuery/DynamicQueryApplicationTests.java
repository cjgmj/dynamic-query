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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;

import com.cjgmj.dynamicQuery.entity.DummyEntity;
import com.cjgmj.dynamicQuery.filter.FieldFilter;
import com.cjgmj.dynamicQuery.filter.TextFieldFilter;
import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.TextPredicate;
import com.cjgmj.dynamicQuery.filter.replace.CharacterReplacement;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class DynamicQueryApplicationTests {

	@PersistenceContext
	private EntityManager em;

	private final String selectQuery = "select d ";
	private final String fromQuery = "from dummy d ";

	private final String like = "%";

	@Test
	void testFilterByName() {
		final FieldFilter fieldFilter = new TextFieldFilter("name", "óh");
		final QueryPredicate queryPredicate = new TextPredicate();

		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<DummyEntity> query = builder.createQuery(DummyEntity.class);
		final Root<DummyEntity> root = query.from(DummyEntity.class);

		final List<DummyEntity> queryExpect = this.em
				.createQuery(this.selectQuery.concat(this.fromQuery).concat(this.createWhereTextQuery())
						.concat(this.normalizeText(fieldFilter.getValue()).concat("'")), DummyEntity.class)
				.getResultList();

		final Predicate predicate = queryPredicate.getPredicateLike(builder, root, fieldFilter);
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

	private String normalizeText(String text) {
		return this.like.concat(Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""))
				.concat(this.like);
	}
}
