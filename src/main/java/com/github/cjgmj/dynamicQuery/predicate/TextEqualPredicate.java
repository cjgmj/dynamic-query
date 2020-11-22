package com.github.cjgmj.dynamicQuery.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class TextEqualPredicate extends TextPredicate {

	@Override
	protected Predicate buildTextPredicate(CriteriaBuilder criteriaBuilder, Expression<String> expression,
			String value) {
		return criteriaBuilder.equal(expression, value);
	}

}
