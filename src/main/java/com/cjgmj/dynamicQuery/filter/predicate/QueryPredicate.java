package com.cjgmj.dynamicQuery.filter.predicate;

import java.text.Normalizer;
import java.time.LocalDateTime;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.FieldFilter;

public abstract class QueryPredicate {

	private final String like = "%";

	private Boolean normalizeText = Boolean.TRUE;

	public Predicate getPredicateEqual(CriteriaBuilder cb, Root<?> root, FieldFilter filter) {
		final String pattern = this.transformTextToQuery(filter.getValue());

		return cb.equal(root.<LocalDateTime>get(filter.getField()), this.getExpression(pattern, cb, root));
	}

	@SuppressWarnings("unchecked")
	public Predicate getPredicateLike(CriteriaBuilder cb, Root<?> root, FieldFilter filter) {
		final String pattern = this.transformTextToQuery(filter.getValue());

		return cb.like((Expression<String>) this.getExpression(filter.getField(), cb, root), pattern);
	}

	public QueryPredicate noNormalizeText() {
		this.normalizeText = Boolean.FALSE;

		return this;
	}

	protected abstract Expression<?> getExpression(String pattern, CriteriaBuilder cb, Root<?> root);

	private String transformTextToQuery(String text) {
		if (this.normalizeText) {
			text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		return this.like.concat(text).concat(this.like);
	}

}
