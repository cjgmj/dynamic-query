package com.github.cjgmj.dynamicquery.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;

@FunctionalInterface
public interface QueryPredicate {

	default Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, ValueFilter<?> valueFilter) {
		return this.buildPredicate(criteriaBuilder, root, valueFilter.getExpression(root), valueFilter);
	}

	Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> valueFilter);

}
