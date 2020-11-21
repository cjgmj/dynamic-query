package com.cjgmj.dynamicQuery.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;

@FunctionalInterface
public interface QueryPredicate {

	Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> fieldFilter);

	default Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, ValueFilter<?> fieldFilter) {
		return this.buildPredicate(criteriaBuilder, root, fieldFilter.getExpression(root), fieldFilter);
	}

}
