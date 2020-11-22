package com.github.cjgmj.dynamicQuery.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.modifier.filter.NumberFilter;

public class NumberPredicate implements QueryPredicate {

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> valueFilter) {
		return criteriaBuilder.equal(expression, ((NumberFilter) valueFilter).getValue());
	}

}
