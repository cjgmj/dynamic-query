package com.cjgmj.dynamicQuery.predicate;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.modifier.filter.DateGreaterThanFilter;

public class DateGreaterThanPredicate implements QueryPredicate {

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> fieldFilter) {
		final DateGreaterThanFilter dateGreaterThanFieldFilter = (DateGreaterThanFilter) fieldFilter;

		if (dateGreaterThanFieldFilter.getOrEqual()) {
			return criteriaBuilder.greaterThanOrEqualTo(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateGreaterThanFieldFilter.getValue()));
		} else {
			return criteriaBuilder.greaterThan(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateGreaterThanFieldFilter.getValue()));
		}
	}
}
