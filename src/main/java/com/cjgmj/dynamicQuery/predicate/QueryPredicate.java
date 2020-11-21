package com.cjgmj.dynamicQuery.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.FieldFilter;

@FunctionalInterface
public interface QueryPredicate {

	Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			FieldFilter<?> fieldFilter);

	default Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, FieldFilter<?> fieldFilter) {
		return this.buildPredicate(criteriaBuilder, root, fieldFilter.getExpression(root), fieldFilter);
	}

}
